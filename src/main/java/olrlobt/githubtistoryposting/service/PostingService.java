package olrlobt.githubtistoryposting.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.Postings;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.utils.CreateBlogUrl;

@Slf4j
@Service
public class PostingService {

	public Postings postings(String blogName) throws IOException {
		Document document = scrapingBlog(blogName);
		return findPostingInfo(document);
	}

	private Document scrapingBlog(String blogName) throws IOException {
		return Jsoup.connect(CreateBlogUrl.tistory(blogName)).get();
	}

	private Postings findPostingInfo(Document document) {
		Elements select = document.select(".list_content");
		Postings postings = new Postings();

		for (Element element : select) {
			Posting posting = new Posting(element);
			postings.add(posting);
		}
		return postings;
	}
}