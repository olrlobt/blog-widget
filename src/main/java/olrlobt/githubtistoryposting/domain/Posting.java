package olrlobt.githubtistoryposting.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import olrlobt.githubtistoryposting.utils.DateUtils;

@Getter
@AllArgsConstructor
public class Posting {

	private final String thumbnail;
	private final String title;
	private final String content;
	private final String footer;
	private final PostingType postingType;

	public Posting(String thumbnail, String title, LocalDate localDate, PostingType postingType) {
		this.thumbnail = thumbnail;
		this.title = title;
		this.content = "";
		this.footer = DateUtils.toString(localDate);
		this.postingType = postingType;
	}

	public Posting(String thumbnail, String title, String content, LocalDate localDate, PostingType postingType) {
		this.thumbnail = thumbnail;
		this.title = title;
		this.content = content;
		this.footer = DateUtils.toString(localDate);
		this.postingType = postingType;
	}

	public Posting(String thumbnail, String title, String footer, PostingType postingType) {
		this.thumbnail = thumbnail;
		this.title = title;
		this.content = "";
		this.footer = footer;
		this.postingType = postingType;
	}

	public static Posting createNoPosting() {
		String thumbnail = BlogInfo.NOT_FIND.getBlogThumb();
		String title = BlogInfo.NOT_FIND.getBlogName();
		String footer = BlogInfo.NOT_FIND.getBlogUrl();
		return new Posting(thumbnail, title, footer, PostingType.BlogPosting);
	}

	@Override
	public String toString() {
		return "Posting{" +
			"title='" + title + '\'' +
			", thumbnail='" + thumbnail + '\'' +
			", date=" + footer +
			'}';
	}
}
