package olrlobt.githubtistoryposting.domain;

import lombok.Getter;

@Getter
public enum BlogInfo {

	NOT_FIND("https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg", "포스팅을 찾을 수 없습니다.", "-"),
	TISTORY("head meta[property=og:image]", "head meta[property=og:site_name]", "head meta[property=og:url]");

	private final String blogThumb;
	private final String blogName;
	private final String blogUrl;

	BlogInfo(String blogThumb, String blogName, String blogUrl) {
		this.blogThumb = blogThumb;
		this.blogName = blogName;
		this.blogUrl = blogUrl;
	}
}
