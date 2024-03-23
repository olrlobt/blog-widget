package olrlobt.githubtistoryposting.service.platform;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class BlogFactory {
	private final Map<String, Blog> blogStrategyMap;

	public BlogFactory(Tistory tistory, Velog velog) {
		blogStrategyMap = new HashMap<>();
		blogStrategyMap.put("tistory", tistory);
		blogStrategyMap.put("t", tistory);
		blogStrategyMap.put("velog", velog);
		blogStrategyMap.put("v", velog);
	}

	public Blog getBlog(String platform) {
		return blogStrategyMap.getOrDefault(platform, null);
	}
}
