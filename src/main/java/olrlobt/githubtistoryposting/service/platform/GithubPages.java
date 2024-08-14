package olrlobt.githubtistoryposting.service.platform;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.domain.PostingBase;
import olrlobt.githubtistoryposting.domain.Watermark;
import olrlobt.githubtistoryposting.utils.DateUtils;
import olrlobt.githubtistoryposting.utils.SvgUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.svg.SVGDocument;
import org.yaml.snakeyaml.Yaml;

@Component
public class GithubPages implements Blog {

    private final String POSTS_PATH = ".github.io/contents/_posts";
    private final String BASE_URL = "https://api.github.com/repos/";
    private final WebClient WEB_CLIENT = WebClient.create();
    @Value("${github.token}")
    private String githubToken;
    @Value("classpath:static/img/github.svg")
    private Resource watermarkImg;
    private Watermark watermark;

    @PostConstruct
    public void init() {
        SVGDocument svgDocument = SvgUtils.loadSVGDocument(watermarkImg);
        watermark = new Watermark(svgDocument, "#000000");
    }

    @Override
    public Posting posting(String blogName, int index, PostingBase postingBase) throws IOException {
        double start = System.currentTimeMillis();
        Posting request = request(BASE_URL + blogName + "/" + blogName + POSTS_PATH, index, blogName);
        double end = System.currentTimeMillis();

        System.out.println("(end - start) = " + (end - start));

        return request;
    }

    private Posting request(String uri, int index, String blogName) {
        List<GithubPagesResponse> githubPagesResponses = WEB_CLIENT.get()
                .uri(UriComponentsBuilder.fromHttpUrl(uri).build(true).toUri())
                .header("Authorization", "Bearer " + githubToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<GithubPagesResponse>>() {
                })
                .block();
        int size = githubPagesResponses.size();
        GithubPagesResponse githubPagesResponse = githubPagesResponses.get(size - index - 1);
        return getPosts(githubPagesResponse, blogName);
    }

    private Posting getPosts(GithubPagesResponse githubPagesResponse, String blogName) {
        try {
            String response = WEB_CLIENT.get()
                    .uri(githubPagesResponse.getDownload_url())
                    .header("Authorization", "Bearer " + githubToken)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return parseResponse(response, blogName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Posting parseResponse(String response, String blogName) {

        String[] parts = response.split("---\\s*\\n", 3);
        String yamlPart = parts[1];
        String contentPart = parts.length > 2 ? parts[2].trim() : "";

        Yaml yaml = new Yaml();
        Map<String, Object> yamlData = yaml.load(yamlPart);

        String siteName = blogName + ".github.io";
        String url = "https://" + siteName;

        return new Posting(null, null, (String) yamlData.get("author"),
                (String) yamlData.get("title"), contentPart, DateUtils.parser((String) yamlData.get("date")),
                url, siteName, PostingBase.BlogPostingCard, watermark);
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
