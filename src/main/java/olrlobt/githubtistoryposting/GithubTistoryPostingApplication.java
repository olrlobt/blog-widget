package olrlobt.githubtistoryposting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class GithubTistoryPostingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubTistoryPostingApplication.class, args);
	}
}

