package olrlobt.githubtistoryposting.service.platform;

import java.util.List;

import lombok.Getter;
import olrlobt.githubtistoryposting.domain.Posting;

@Getter
public class VelogResponse {
	private Data data;

	@Getter
	public static class Data {
		private List<Post> posts;
		private User user;
		private Profile profile;
	}

	@Getter
	public static class Post {
		private String title;
		private String thumbnail;
		private String short_description;
		private String url_slug;
		private String released_at;
		private int comments_count;
		private List<String> tags;
		private int likes;
		private User user;
	}

	@Getter
	public static class User {
		private String username;
		private Profile profile;
	}

	@Getter
	public static class Profile {
		private String thumbnail;
	}
}
