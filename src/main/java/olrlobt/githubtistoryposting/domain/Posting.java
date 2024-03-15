package olrlobt.githubtistoryposting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Posting {

	private final String thumbnail;
	private final String title;
	private final String footer;

	public static Posting createNoPosting(){
		String thumbnail = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg";
		String title = "포스팅이 존재하지 않습니다.";
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
