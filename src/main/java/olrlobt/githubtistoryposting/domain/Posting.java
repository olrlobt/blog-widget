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
	private final String footer;

	public Posting(String thumbnail, String title, LocalDate localDate) {
		this.thumbnail = thumbnail;
		this.title = title;
		this.footer = DateUtils.toString(localDate);;
	}

	public static Posting createNoPosting() {
		String thumbnail = BlogInfo.NOT_FIND.getBlogThumb();
		String title = BlogInfo.NOT_FIND.getBlogName();
		String footer = BlogInfo.NOT_FIND.getBlogUrl();
		return new Posting(thumbnail, title, footer);
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
