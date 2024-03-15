package olrlobt.githubtistoryposting.utils;

public class UrlUtils {

	public static String createTistoryUrl(String blogName) {
		return "https://" + blogName + ".tistory.com";
	}

	public static String createTistoryUrl(String location, int page) {
		return location.split("/\\?page")[0] + "/?page=" + page;
	}

	public static String of(String blogName, String url) {
		return createTistoryUrl(blogName) + "/" + url;
	}

	public static String changeThumbnailSize(String url, String size) {
		int thumbIndex = url.indexOf("thumb/");
		String modifiedUrl = url.substring(0, thumbIndex + 6) + size;
		String param = url.split("/\\?")[1];
		return modifiedUrl + "/?" + param;
	}

	public static String addProtocol(String url) {
		return "https:" + url;
	}
}
