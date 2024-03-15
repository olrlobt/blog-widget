package olrlobt.githubtistoryposting.domain;

import lombok.Getter;

@Getter
public enum BlogTag {
	TISTORY("head meta[property=og:image]", "head meta[property=og:site_name]", "head meta[property=og:url]", ".list_content",
		".tit_post", ".txt_date", "img", ".list_content .link_post");

	private final String blogThumb;
	private final String blogName;
	private final String blogUrl;
	private final String postingList;
	private final String postingTitle;
	private final String postingDate;
	private final String postingThumb;
	private final String postingLink;

	BlogTag(String blogThumb, String blogName, String blogUrl, String postingList, String postingTitle,
		String postingDate, String postingThumb, String postingLink) {
		this.blogThumb = blogThumb;
		this.blogName = blogName;
		this.blogUrl = blogUrl;
		this.postingList = postingList;
		this.postingTitle = postingTitle;
		this.postingDate = postingDate;
		this.postingThumb = postingThumb;
		this.postingLink = postingLink;
	}
}
