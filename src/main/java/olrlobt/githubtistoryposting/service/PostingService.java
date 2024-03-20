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
import olrlobt.githubtistoryposting.domain.BlogInfo;
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

		BlogTag theme = findTistoryTheme(document);
		return findPosting(document, index, theme);
	}

	private BlogTag findTistoryTheme(Document document) {
		String themeIndex = document.selectFirst("body").className().split(" ")[0];
		if (themeIndex.isEmpty()) {
			themeIndex = document.selectFirst("html").id();
			if (themeIndex.isEmpty()) {
				themeIndex = document.selectFirst(".emph_t").text();
			}
		}
		return BlogTag.findTheme(themeIndex);
	}

	private Posting findPosting(Document document, int index, BlogTag theme) throws IOException {
		int page = 1;
		DocumentCurrent current = getCurrentDocument(index, document, page, theme.getPostingList());
		Elements postings = current.document.select(theme.getPostingList());

		if (current.index >= postings.size()) {
			return Posting.createNoPosting();
		}
		return makePosting(current.index, postings, theme);
	}

	private Posting makePosting(int index, Elements postings, BlogTag theme) {
		Element posting = postings.get(index);

		String thumbnail = null;
		Element thumb = posting.select(theme.getPostingThumb()).first();
		if (thumb != null) {
			String src = thumb.attr("src");
			if (!src.isEmpty()) {
				thumbnail = UrlUtils.changeThumbnailSize(src, ImageSize.TistoryPosting.getSizeParam());
				thumbnail = UrlUtils.addProtocol(thumbnail);
			}else{
				thumbnail = thumb.attr("data-src").replace("amp;","");
			}
		}

		String title = posting.select(theme.getPostingTitle()).text();
		LocalDate date = DateUtils.parser(posting.select(theme.getPostingDate()).text());
		return new Posting(thumbnail, title, date);
	}

	public RedirectView getPostingLink(String blogName, int index) throws IOException {
		int page = 1;
		Document document = scrapingService.scrapingBlog(blogName);
		BlogTag theme = findTistoryTheme(document);

		DocumentCurrent current = getCurrentDocument(index, document, page, theme.getPostingLink());
		Elements postings = current.document.select(theme.getPostingLink());
		if (current.index >= postings.size()) {
			String url = current.document.select(BlogInfo.TISTORY.getBlogUrl())
				.attr("content");
			return new RedirectView(url);
		}
		String postingParam = postings
			.get(current.index)
			.attr("href");

		return new RedirectView(UrlUtils.of(blogName, postingParam));
	}

	private DocumentCurrent getCurrentDocument(int index, Document document, int page, String postingLink) throws
		IOException {
		Elements postings = document.select(postingLink);
		int postingNumOfPage = postings.size();

		while (postingNumOfPage <= index && !postings.isEmpty()) {
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

		String originalThumb = document.select(BlogInfo.TISTORY.getBlogThumb())
			.attr("content");
		String resizeThumbnail = UrlUtils.changeThumbnailSize(originalThumb, ImageSize.TistoryBlog.getSizeParam());
		String title = document.select(BlogInfo.TISTORY.getBlogName())
			.attr("content");
		String footer = document.select(BlogInfo.TISTORY.getBlogUrl())
			.attr("content");

		return new Posting(resizeThumbnail, title, footer);
	}
}