package olrlobt.githubtistoryposting.api;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.service.ImageService;
import olrlobt.githubtistoryposting.service.PostingService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostingApi {

	private final PostingService postingService;
	private final ImageService imageService;

	@GetMapping("/api/posting/{index}")
	public ResponseEntity<byte[]> getPosting(@RequestParam String blogName, @PathVariable int index) throws IOException {
		Posting posting = postingService.posting(blogName, index);
		byte[] svgImageBox = imageService.createSvgImageBox(posting);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("image/svg+xml"));
		headers.setCacheControl("no-cache");
		return new ResponseEntity<>(svgImageBox, headers, HttpStatus.OK);
	}

	@GetMapping("/api/posting-link/{index}")
	public RedirectView getPostingLink(@RequestParam String blogName, @PathVariable int index) throws IOException {
		return postingService.getPostingLink(blogName, index);
	}
}
