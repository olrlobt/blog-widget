package olrlobt.githubtistoryposting.utils;

public class CreateBlogUrl {

	public static String tistory(String blogName) {
		return "https://" + blogName + ".tistory.com";
	}

	public static String of(String blogName, String url) {
		return "https://" + blogName + ".tistory.com/" + url;
	}
}
