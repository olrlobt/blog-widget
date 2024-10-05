package olrlobt.githubtistoryposting.utils;

import java.io.IOException;

import java.io.InputStream;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ScrapingUtils {
	private final OkHttpClient client;

	public ScrapingUtils() {
		this.client = new OkHttpClient.Builder()
				.retryOnConnectionFailure(true) // 연결 실패 시 재시도
				.build();
	}

	@Cacheable(cacheNames = "url", key = "#url", sync = true)
	public Document byUrl(String url) throws IOException {
		Request request = new Request.Builder()
				.url(url)
				.build();

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new IOException("Failed to download content from URL: " + url + ", Response: " + response);
			}

			ResponseBody body = response.body();
			if (body == null) {
				throw new IOException("Response body is null for URL: " + url);
			}

			try (InputStream inputStream = body.byteStream()) {
				Document parse = Jsoup.parse(inputStream, "UTF-8", url);
				parse.select("script, style").remove();
				return parse;
			}
		} catch (IOException e) {
			throw e;
		}
	}

}

