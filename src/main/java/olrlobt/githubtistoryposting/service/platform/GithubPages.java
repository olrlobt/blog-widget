package olrlobt.githubtistoryposting.service.platform;

import jakarta.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.domain.PostingBase;
import olrlobt.githubtistoryposting.domain.Watermark;
import olrlobt.githubtistoryposting.service.platform.GithubPagesResponse.Entry;
import olrlobt.githubtistoryposting.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;
import org.yaml.snakeyaml.Yaml;

@Component
public class GithubPages implements Blog {

    private final WebClient WEB_CLIENT = WebClient.builder()
            .baseUrl("https://api.github.com/graphql")
            .build();
    private final String QUERY_POSTINGS =
            "query($username: String!, $repository: String!, $expression: String!) { "
                    + "user(login: $username) { avatarUrl } "
                    + "repository(owner: $username, name: $repository) { "
                    + "object(expression: $expression) { ... on Tree { entries { name } } } } }";

    private final String QUERY_POSTING =
            "query($username: String!, $repository: String!, $expression: String!) { "
                    + "repository(owner: $username, name: $repository) { "
                    + "object(expression: $expression) { ... on Blob { text } } } }";


    @Value("${github.token}")
    private String githubToken;
    @Value("classpath:static/img/github.svg")
    private Resource watermarkImg;
    @Value("classpath:static/img/github.png")
    private Resource watermarkPngImg;
    private Watermark watermark;

    @PostConstruct
    public void init() {
        try {
            BufferedImage watermarkImage = ImageIO.read(watermarkPngImg.getInputStream());
            watermark = new Watermark(watermarkImage);
        } catch (IOException e) {
//            log.error("Failed to load PNG watermark image", e);
        }
    }

    @Override
    public Posting posting(String blogName, int index, PostingBase postingBase) throws IOException {

        Map<String, Object> variables = new HashMap<>();
        variables.put("username", blogName);
        variables.put("repository", blogName + ".github.io");
        variables.put("expression", "HEAD:_posts");

        GithubPagesResponse response = request(QUERY_POSTINGS, variables);
        List<Entry> entries = response.getData().getRepository()
                .getObject().getEntries();
        Entry post = entries.get(entries.size() - index - 1);

        variables.put("expression", "HEAD:_posts/" + post.getName());

        GithubPagesResponse response2 = request(QUERY_POSTING, variables);
        String content = response2.getData().getRepository()
                .getObject().getText();
        String avatarUrl = response.getData().getUser().getAvatarUrl();
        return parseResponse(content, blogName, avatarUrl, postingBase);
    }

    private GithubPagesResponse request(String query, Map<String, Object> variables) {
        return WEB_CLIENT.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + githubToken)
                .bodyValue(Map.of("query", query, "variables", variables))
                .retrieve()
                .bodyToMono(GithubPagesResponse.class)
                .block();
    }

    private Posting parseResponse(String response, String blogName, String avatarUrl, PostingBase postingBase) {
        String[] parts = response.split("---\\s*\\n", 3);
        String yamlPart = parts[1];
        String contentPart = parts.length > 2 ? parts[2].trim() : "";

        Yaml yaml = new Yaml();
        Map<String, Object> yamlData = yaml.load(yamlPart);

        String siteName = blogName + ".github.io";
        String url = "https://" + siteName;

        return new Posting(null, avatarUrl, (String) yamlData.get("author"),
                (String) yamlData.get("title"), contentPart, DateUtils.parser((String) yamlData.get("date")),
                url, siteName, postingBase, watermark);
    }

    @Override
    public RedirectView link(String blogName, int index) throws IOException {
        return null;
    }

    @Override
    public Posting blog(String blogName) throws IOException {
        return null;
    }
}
