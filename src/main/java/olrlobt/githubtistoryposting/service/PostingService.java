package olrlobt.githubtistoryposting.service;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.utils.CreateBlogUrl;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingService {

	private final ScrapingService scrapingService;

	public Posting posting(String blogName, int index) throws IOException {
		Document document = scrapingService.scrapingBlog(blogName);
		return findPostingInfo(document, index);
	}

	private Posting findPostingInfo(Document document, int index) {
		Elements select = document.select(".list_content");
		return new Posting(select.get(index));
	}

	public RedirectView getPostingLink(String blogName, int index) throws IOException {
		Document document = scrapingService.scrapingBlog(blogName);
		String attr = document.select(".list_content")
			.get(index)
			.select("a")
			.attr("href");

		return new RedirectView(CreateBlogUrl.of(blogName, attr));
	}
}