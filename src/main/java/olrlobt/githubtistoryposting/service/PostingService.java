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

	public void postings(String blogName) throws IOException {
		Postings postings = findPosting(CreateBlogUrl.tistory(blogName));
	}

	private Postings findPosting(String blogUrl) throws IOException {
		Document document = Jsoup.connect(blogUrl).get();
		Elements select = document.select(".list_content");
		Postings postings = new Postings();

		for (Element element : select) {
			Posting posting = new Posting(element);
			postings.add(posting);
		}
		return postings;
	}
}