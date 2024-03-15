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
		Elements postings = document.select(BlogTag.TISTORY.getPostingList());
		int postingNumOfPage = postings.size();

		while (postingNumOfPage <= index && !postings.isEmpty()){
			index -= postingNumOfPage;
			page++;
		}
		document = scrapingService.scrapingBlog(document.location(), page);
		postings = document.select(BlogTag.TISTORY.getPostingList());

		if (index >= postings.size()) {
			return Posting.createNoPosting();
		}
		return makePosting(index, postings);
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
		Elements postings = document.select(BlogTag.TISTORY.getPostingLink());

		int postingNumOfPage = postings.size();

		while (postingNumOfPage <= index && !postings.isEmpty()){
			index -= postingNumOfPage;
			page++;
		}

		document = scrapingService.scrapingBlog(document.location(), page);
		postings = document.select(BlogTag.TISTORY.getPostingLink());

		log.info("========= {}" , postings.toString());
		if (index >= postings.size()) {
			// 에러
		}
		String postingParam = postings
			.get(index)
			.attr("href");

		log.info("============= {} ", postingParam);
		return new RedirectView(UrlUtils.of(blogName, postingParam));
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