package olrlobt.githubtistoryposting.service.platform;

import java.io.IOException;

import org.springframework.web.servlet.view.RedirectView;

import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.domain.PostingType;

public interface Blog {
	Posting posting(String blogName, int index, PostingType postingType) throws IOException;
	RedirectView link(String blogName, int index) throws IOException;
	Posting blog(String blogName) throws IOException;
}
