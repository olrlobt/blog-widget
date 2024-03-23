package olrlobt.githubtistoryposting.service.platform;

import java.io.IOException;

import org.springframework.web.servlet.view.RedirectView;

import olrlobt.githubtistoryposting.domain.Posting;

public interface Blog {
	Posting posting(String blogName, int index) throws IOException;
	RedirectView link(String blogName, int index) throws IOException;
	Posting blog(String blogName) throws IOException;
}
