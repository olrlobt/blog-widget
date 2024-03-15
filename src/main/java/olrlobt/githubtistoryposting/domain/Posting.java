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
		String thumbnail = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg";
		String title = "포스팅을 찾을 수 없습니다.";
		String footer = "-";
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
