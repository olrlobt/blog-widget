package olrlobt.githubtistoryposting.api;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ProfileController {

	private final Environment env;

	@GetMapping("/profile")
	public String getActiveProfiles(){
		return Arrays.stream(env.getActiveProfiles())
			.findFirst()
			.orElse("No active profile");
	}
}
