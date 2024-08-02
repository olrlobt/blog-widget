package olrlobt.githubtistoryposting.service;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.domain.PostingType;
import olrlobt.githubtistoryposting.service.platform.Blog;
import olrlobt.githubtistoryposting.service.platform.BlogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingService {

	private final BlogFactory blogFactory;

	public Posting posting(String blogName, String platform, int index, String theme) throws IOException {
		Blog blog = blogFactory.getBlog(platform);
		PostingType themeName = PostingType.getTheme(theme);
		return blog.posting(blogName, index, themeName);
	}

	public RedirectView link(String blogName, String platform, int index) throws IOException {
		Blog blog = blogFactory.getBlog(platform);
		return blog.link(blogName, index);
	}

	public Posting blog(String blogName, String platform) throws IOException {
		Blog blog = blogFactory.getBlog(platform);
		return blog.blog(blogName);
	}

	public Posting anything(String url, String theme) throws IOException {
		return posting(url, "else", 0, theme);
	}
}