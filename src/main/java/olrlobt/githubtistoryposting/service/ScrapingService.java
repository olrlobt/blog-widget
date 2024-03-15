package olrlobt.githubtistoryposting.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.utils.UrlUtils;

@Service
@Slf4j
public class ScrapingService {

	@Cacheable(cacheNames = "blog", key = "#blogName", sync = true)
	public Document scrapingBlog(String blogName) throws IOException {
		log.info("Scraping blog: {}", blogName);
		return Jsoup.connect(UrlUtils.createTistoryUrl(blogName)).get();
	}

	@Cacheable(cacheNames = "blog", key = "#url.concat('-').concat(#page)", sync = true)
	public Document scrapingBlog(String url, int page) throws IOException {
		return Jsoup.connect(UrlUtils.createTistoryUrl(url, page)).get();
	}
}
