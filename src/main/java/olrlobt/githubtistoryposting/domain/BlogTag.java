package olrlobt.githubtistoryposting.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum BlogTag {
	Odyssey("headerslogundisplayon", "article", ".title", ".date", "img", ".article-content .link-article"),
	Poster("color-gray", ".post-item", ".title", "head meta[property=og:image]", "img", ".post-item a"),
	Whatever("layout-wide", ".post-item", ".title", "head meta[property=og:image]", "img", ".post-item a"),
	Letter("thema_aqua", ".article_content", ".title_post", ".date", ".thumbnail_post", ".article_content .link_article"),
	Portfolio("theme_pink", ".item_category", ".name", ".date", ".thumnail", ".item_category a"),
	BookClub("layout-aside-right", ".post-item", ".title", ".date", "img", ".post-item a"),
	Magazine("theme_red", ".link_thumb", ".txt_thumb", ".date", ".thumb_img", ".link_thumb a"),
	TISTORY_2("kakao", ".list_content", ".tit_post", ".txt_date", "img", ".list_content .link_post"),
	TISTORY_1("Tistory", ".list_content", ".tit_post", ".detail_info", "img", ".link_post"),
	HELLO("__hELLO", ".item", ".title a", ".date time", "img", ".item .title a"),
	;

	private final String blogTheme;
	private final String postingList;
	private final String postingTitle;
	private final String postingDate;
	private final String postingThumb;
	private final String postingLink;
	private static final Map<String, BlogTag> blogTags;

	static {
		blogTags = new HashMap<>();
		for (BlogTag tag : values()) {
			blogTags.put(tag.getBlogTheme(), tag);
		}
	}

	BlogTag(String blogTheme, String postingList, String postingTitle, String postingDate, String postingThumb,
		String postingLink) {
		this.blogTheme = blogTheme;
		this.postingList = postingList;
		this.postingTitle = postingTitle;
		this.postingDate = postingDate;
		this.postingThumb = postingThumb;
		this.postingLink = postingLink;
	}

	public static BlogTag findTheme(String blogTheme) {
		return blogTags.get(blogTheme);
	}
}
