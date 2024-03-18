package olrlobt.githubtistoryposting.domain;

import lombok.Getter;

@Getter
public enum BlogInfo {

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
