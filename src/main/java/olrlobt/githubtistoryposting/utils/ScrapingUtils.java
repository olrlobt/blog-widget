package olrlobt.githubtistoryposting.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class ScrapingUtils {
	private final OkHttpClient client;

	public ScrapingUtils() {
		this.client = new OkHttpClient.Builder()
				.retryOnConnectionFailure(true) // 연결 실패 시 재시도
				.build();
	}

	@Cacheable(cacheNames = "url", key = "#url", sync = true)
	public Document byUrl(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();

		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful() && response.body() != null) {
				return Jsoup.parse(response.body().string());
			}
		}
		return null;
	}
}

