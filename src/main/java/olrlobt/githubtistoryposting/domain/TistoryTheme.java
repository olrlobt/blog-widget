package olrlobt.githubtistoryposting.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum TistoryTheme {
	Odyssey("headerslogundisplayon", "article", ".title", ".date", "img", ".summary", ".article-content .link-article"),
	Poster("color-gray", ".post-item", ".title", "head meta[property=og:image]", "img",".excerpt" , ".post-item a"),
	Whatever("layout-wide", ".post-item", ".title", "head meta[property=og:image]", "img", ".excerpt", ".post-item a"),
	Letter("thema_aqua", ".article_content", ".title_post", ".date", ".thumbnail_post", ".txt_post",
			".article_content .link_article"),
	Portfolio("theme_pink", ".item_category", ".name", ".date", ".thumnail",".text" , ".item_category a"),
	BookClub("layout-aside-right", ".post-item", ".title", ".date", "img", ".excerpt" , ".post-item a"),
	Magazine("theme_red", ".link_thumb", ".txt_thumb", ".date", ".thumb_img",".txt_thumb" , ".link_thumb a"),
	TISTORY_2("kakao", ".list_content", ".tit_post", ".txt_date", "img", ".txt_post", ".list_content .link_post"),
	TISTORY_1("Tistory", ".list_content", ".tit_post", ".detail_info", "img", ".txt_post" , ".link_post"),
	HELLO("__hELLO", ".item", ".title a", ".date time", "img",".summary", ".item .title a"),
	;

	private final String blogTheme;
	private final String postingList;
	private final String postingTitle;
	private final String postingDate;
	private final String postingThumb;
	private final String postingContent;
	private final String postingLink;
	private static final Map<String, TistoryTheme> blogTags;

	static {
		blogTags = new HashMap<>();
		for (TistoryTheme tag : values()) {
			blogTags.put(tag.getBlogTheme(), tag);
		}
	}

	TistoryTheme(String blogTheme, String postingList, String postingTitle, String postingDate, String postingThumb,
				 String postingContent,
				 String postingLink) {
		this.blogTheme = blogTheme;
		this.postingList = postingList;
		this.postingTitle = postingTitle;
		this.postingDate = postingDate;
		this.postingThumb = postingThumb;
        this.postingContent = postingContent;
        this.postingLink = postingLink;
	}

	public static TistoryTheme findTheme(String blogTheme) {
		return blogTags.get(blogTheme);
	}
}
