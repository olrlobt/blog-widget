package olrlobt.githubtistoryposting.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ScrapingUtils {
	@Cacheable(cacheNames = "url", key = "#url", sync = true)
	public Document byUrl(String url) throws IOException {
		return Jsoup.connect(url).get();
	}
}
