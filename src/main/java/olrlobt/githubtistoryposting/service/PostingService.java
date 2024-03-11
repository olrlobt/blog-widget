package olrlobt.githubtistoryposting.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.utils.CreateBlogUrl;

@Slf4j
@Service
public class PostingService {

	public Posting posting(String blogName, int index) throws IOException {
		Document document = scrapingBlog(blogName);
		return findPostingInfo(document, index);
	}

	private Document scrapingBlog(String blogName) throws IOException {
		return Jsoup.connect(CreateBlogUrl.tistory(blogName)).get();
	}

	private Posting findPostingInfo(Document document, int index) {
		Elements select = document.select(".list_content");
		return new Posting(select.get(index));
	}
}