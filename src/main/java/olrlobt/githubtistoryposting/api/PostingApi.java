package olrlobt.githubtistoryposting.api;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.Postings;
import olrlobt.githubtistoryposting.service.ImageService;
import olrlobt.githubtistoryposting.service.PostingService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostingApi {

	private final PostingService postingService;
	private final ImageService imageService;

	@GetMapping("/api/postings")
	public void getPostings(@RequestParam String blogName) throws IOException {
		log.info(blogName);
		Postings postings = postingService.postings(blogName);
		imageService.getImageBox(postings);
	}
}
