package olrlobt.githubtistoryposting.service.platform;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;

import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.utils.DateUtils;

@Component
public class Velog implements Blog {

	private final WebClient webClient = WebClient.builder()
		.baseUrl("https://v2.velog.io/graphql")
		.build();

	@Override
	public Posting posting(String blogName, int index) {
		String query =
			"query Posts($cursor: ID, $username: String, $temp_only: Boolean, $tag: String, $limit: Int) {\n" +
				"posts(cursor: $cursor, username: $username, temp_only: $temp_only, tag: $tag, limit: $limit) {title"
				+ " thumbnail user { username profile{thumbnail}} url_slug released_at comments_count tags likes}}";

		Map<String, Object> variables = Map.of(
			"username", blogName,
			"limit", index + 1
		);

		VelogResponse response = webClient.post()
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(Map.of("query", query, "variables", variables))
			.retrieve()
			.bodyToMono(VelogResponse.class)
			.block();

		VelogResponse.Post post = response.getData().getPosts().get(index);
		return new Posting(post.getThumbnail(), post.getTitle(), DateUtils.parser(post.getReleased_at()));
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
