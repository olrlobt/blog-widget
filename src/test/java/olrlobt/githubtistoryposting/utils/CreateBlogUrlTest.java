package olrlobt.githubtistoryposting.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateBlogUrlTest {

	@Test
	@DisplayName("티스토리 블로그 링크 생성")
	void tistory() {

		//given
		String name = "olrlobt";
		//when
		String blogUrl = CreateBlogUrl.tistory(name);
		//then
		assertThat(blogUrl).isEqualTo("https://olrlobt.tistory.com");
	}
}