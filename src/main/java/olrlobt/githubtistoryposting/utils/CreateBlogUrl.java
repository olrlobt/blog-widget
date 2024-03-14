package olrlobt.githubtistoryposting.utils;

public class CreateBlogUrl {

	public static String tistory(String blogName) {
		return "https://" + blogName + ".tistory.com";
	}

	public static String tistory(String location, int page) {
		return location.split("/\\?page")[0] + "/?page=" + page;
	}

	public static String of(String blogName, String url) {
		return "https://" + blogName + ".tistory.com/" + url;
	}
}
