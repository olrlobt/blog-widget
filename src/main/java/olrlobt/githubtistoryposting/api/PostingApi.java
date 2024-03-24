package olrlobt.githubtistoryposting.api;

import static org.springframework.http.HttpStatus.*;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
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
import olrlobt.githubtistoryposting.domain.PostingType;
import olrlobt.githubtistoryposting.service.ImageService;
import olrlobt.githubtistoryposting.service.PostingService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostingApi {

	private final PostingService postingService;
	private final ImageService imageService;

	@GetMapping("/api/{platform}/posting/{index}")
	public ResponseEntity<byte[]> getPosting(@RequestParam String name,
		@PathVariable String platform,
		@PathVariable int index) throws IOException {
		Posting posting = postingService.posting(name, platform, index);
		byte[] svgImageBox = imageService.createSvgImageBox(posting);

		return new ResponseEntity<>(svgImageBox, setHeader(), OK);
	}

	@GetMapping("/api/{platform}/link/{index}")
	public RedirectView getPostingLink(@RequestParam String name,
		@PathVariable String platform,
		@PathVariable int index) throws IOException {
		return postingService.link(name, platform, index);
	}

	@GetMapping("/api/{platform}/blog")
	public ResponseEntity<byte[]> getBlogInfo(@RequestParam String name,
		@PathVariable String platform) throws IOException {
		Posting postingInfo = postingService.blog(name, platform);
		byte[] svgImageBox = imageService.createSvgImageBox(postingInfo);

		return new ResponseEntity<>(svgImageBox, setHeader(), OK);
	}

	@GetMapping("/api/fix")
	public ResponseEntity<byte[]> getAnythingElse(@RequestParam String url, @RequestParam(required = false) String theme) throws IOException {
		Posting postingInfo = postingService.anything(url, theme);
		byte[] svgImageBox = imageService.createSvgImageBox(postingInfo);

		return new ResponseEntity<>(svgImageBox, setHeader(), OK);
	}

	private static HttpHeaders setHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("image/svg+xml"));
		headers.setCacheControl("no-cache");
		return headers;
	}
}
