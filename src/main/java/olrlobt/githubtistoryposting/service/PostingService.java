package olrlobt.githubtistoryposting.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.service.platform.Tistory;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingService {

	private final Tistory tistory;

	public Posting posting(String blogName, String platform, int index) throws IOException {
		return tistory.posting(blogName, index);
	}

	public Posting getPostingInfo(String blogName, String platform) throws IOException {
		return tistory.blog(blogName);
	}

	public RedirectView getPostingLink(String blogName, String platform, int index) throws IOException {
		return tistory.link(blogName, index);
	}
}