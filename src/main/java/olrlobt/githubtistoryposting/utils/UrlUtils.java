package olrlobt.githubtistoryposting.utils;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlUtils {

    public static String createTistoryUrl(String blogName) {
        return "https://" + blogName + ".tistory.com";
    }

    public static String createTistoryUrl(String location, int page) {
        return location.split("/\\?page")[0] + "/?page=" + page;
    }

    public static String of(String blogName, String url) {
        return createTistoryUrl(blogName) + url;
    }

    public static String changeThumbnailSize(String url, String size) {
        int thumbIndex = url.indexOf("thumb/");
        String modifiedUrl = url.substring(0, thumbIndex + 6) + size;
        String param = url.split("/\\?")[1];
        return modifiedUrl + "/?" + param;
    }

    public static String addProtocol(String url) {
        return url.substring(0, 6).contains("https:") ? url : "https:" + url;
    }

    public static String encodeByKorean(String target) {
        return URLEncoder.encode(target, StandardCharsets.UTF_8);
    }

    public static String decodeByKorean(String target) {
        return URLDecoder.decode(target, StandardCharsets.UTF_8);
    }

    public static String getSiteName(String urlString) {
        try {
            URL url = new URL(urlString);
            String host = url.getHost();

            // www. 또는 m. 같은 서브도메인을 제거하여 주요 도메인만 남기기
            if (host.startsWith("www.")) {
                host = host.substring(4);
            } else if (host.startsWith("m.")) {
                host = host.substring(2);
            }

            return host;
        } catch (Exception e) {
            return "";
        }
    }

    public static String encodeLastPathSegment(String url) {
        int lastSlashIndex;
        if (url == null || (lastSlashIndex = url.lastIndexOf('/')) == -1) {
            return url;
        }
        String baseUrl = url.substring(0, lastSlashIndex + 1);
        String lastSegment = url.substring(lastSlashIndex + 1);
        String encodedLastSegment = encodeByKorean(lastSegment);

        return baseUrl + encodedLastSegment;
    }

    public static String sanitizeUrl(String url) {
        int questionMarkIndex = url.indexOf("?");
        return (questionMarkIndex != -1) ? url.substring(0, questionMarkIndex) : url;
    }
}
