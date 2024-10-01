package olrlobt.githubtistoryposting.config;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		cacheManager.setCaffeine(caffeineCacheBuilder());
		return cacheManager;
	}

	private Caffeine<Object, Object> caffeineCacheBuilder() {
		return Caffeine.newBuilder()
			.initialCapacity(30)
			.maximumSize(50)
			.expireAfterAccess(30, TimeUnit.MINUTES);
	}

	@PostConstruct
	public void disableImageIOCache() {
		ImageIO.setUseCache(false);
	}
}