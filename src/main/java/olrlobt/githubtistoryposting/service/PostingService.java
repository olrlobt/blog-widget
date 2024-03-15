package olrlobt.githubtistoryposting.service;

import java.io.IOException;
import java.time.LocalDate;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.BlogTag;
import olrlobt.githubtistoryposting.domain.ImageSize;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.utils.DateUtils;
import olrlobt.githubtistoryposting.utils.UrlUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingService {

	private final ScrapingService scrapingService;

	public Posting posting(String blogName, int index) throws IOException {
		Document document = scrapingService.scrapingBlog(blogName);
		return findPosting(document, index);
	}

	private Posting findPosting(Document document, int index) throws IOException {
		int page = 1;
		DocumentCurrent current = getCurrentDocument(index, document, page, BlogTag.TISTORY.getPostingList());
		Elements postings = current.document.select(BlogTag.TISTORY.getPostingList());

		if (current.index >= postings.size()) {
			return Posting.createNoPosting();
		}
		return makePosting(current.index, postings);
	}

	private static Posting makePosting(int index, Elements postings) {
		Element posting = postings.get(index);

		String thumbnail = null;
		Element first = posting.select(BlogTag.TISTORY.getPostingThumb()).first();
		if (first != null) {
			String src = first.attr("src");
			thumbnail = UrlUtils.changeThumbnailSize(src, ImageSize.TistoryPosting.getSizeParam());
			thumbnail = UrlUtils.addProtocol(thumbnail);
		}

		String title = posting.select(BlogTag.TISTORY.getPostingTitle()).text();
		LocalDate parser = DateUtils.parser(posting.select(BlogTag.TISTORY.getPostingDate()).text());
		return new Posting(thumbnail, title, parser);
	}

	public RedirectView getPostingLink(String blogName, int index) throws IOException {
		int page = 1;
		Document document = scrapingService.scrapingBlog(blogName);
		DocumentCurrent current = getCurrentDocument(index, document, page, BlogTag.TISTORY.getPostingLink());
		Elements postings;
		postings = current.document.select(BlogTag.TISTORY.getPostingLink());

		if (current.index >= postings.size()) {
			String url = current.document.select(BlogTag.TISTORY.getBlogUrl())
				.attr("content");
			return new RedirectView(url);
		}
		String postingParam = postings
			.get(current.index)
			.attr("href");

		return new RedirectView(UrlUtils.of(blogName, postingParam));
	}

	private DocumentCurrent getCurrentDocument(int index, Document document, int page, String postingLink) throws IOException {
		Elements postings = document.select(postingLink);

		int postingNumOfPage = postings.size();

		while (postingNumOfPage <= index && !postings.isEmpty()){
			index -= postingNumOfPage;
			page++;
		}

		document = scrapingService.scrapingBlog(document.location(), page);
		return new DocumentCurrent(index, document);
	}

	private record DocumentCurrent(int index, Document document) {
	}

	public Posting getPostingInfo(String blogName) throws IOException {
		Document document = scrapingService.scrapingBlog(blogName);

		String originalThumb = document.select(BlogTag.TISTORY.getBlogThumb())
			.attr("content");
		String resizeThumbnail = UrlUtils.changeThumbnailSize(originalThumb, ImageSize.TistoryBlog.getSizeParam());
		String title = document.select(BlogTag.TISTORY.getBlogName())
			.attr("content");
		String footer = document.select(BlogTag.TISTORY.getBlogUrl())
			.attr("content");

		return new Posting(resizeThumbnail, title, footer);
	}
}