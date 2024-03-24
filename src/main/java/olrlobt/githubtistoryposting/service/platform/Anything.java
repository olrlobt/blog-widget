package olrlobt.githubtistoryposting.service.platform;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.domain.PostingType;
import olrlobt.githubtistoryposting.utils.DateUtils;
import olrlobt.githubtistoryposting.utils.ScrapingUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class Anything implements Blog {

	private final ScrapingUtils scrapingUtils;

	@Override
	public Posting posting(String url, int index) throws IOException {
		Document document = scrapingUtils.byUrl(url);
		String thumb = document.select("head meta[property=og:image]").attr("content");
		String title = document.select("head meta[property=og:title]").attr("content");
		String footer = document.select("head meta[property=article:published_time]").attr("content");

		if (!footer.isEmpty()) {
			return  new Posting(thumb, title, DateUtils.parser(footer), PostingType.BlogPosting);
		}

		footer = document.select("head meta[property=og:url]").attr("content");
		return new Posting(thumb, title, footer, PostingType.BlogPosting);
	}

	@Override
	public RedirectView link(String url, int index) {
		return new RedirectView(url);
	}

	@Override
	public Posting blog(String url) throws IOException {
		Document document = scrapingUtils.byUrl(url);
		String thumb = document.select("head meta[property=og:image]").attr("content");
		String title = document.select("head meta[property=og:title]").attr("content");
		String footer = document.select("head meta[property=og:url]").attr("content");
		if (!footer.isEmpty()) {
			return  new Posting(thumb, title, footer, PostingType.BlogInfo);
		}

		footer = document.select("head meta[property=og:site_name]").attr("content");
		return new Posting(thumb, title, footer, PostingType.BlogInfo);
	}
}
