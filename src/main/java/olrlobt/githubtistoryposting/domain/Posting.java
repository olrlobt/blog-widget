package olrlobt.githubtistoryposting.domain;

import java.time.LocalDate;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.Getter;
import olrlobt.githubtistoryposting.utils.DateUtils;

@Getter
public class Posting {

	private final String title;
	private final String thumbnail;
	private final LocalDate date;

	public Posting(Element element) {
		title = element.select(".tit_post ").text();

		String thumb = null;
		Elements select = element.select("img");
		if (!select.isEmpty()) {
			String[] split = select
				.attr("src")
				.split("/\\?");

			String img217x122 = split[0].split("C")[0] + "C217x122";
			thumb = "https:" + img217x122 + "/?" + split[1];
		}
		thumbnail = thumb;

		date = DateUtils.parser(element.select(".txt_date").text());
	}

	public Posting() {
		this.title = "포스팅이 존재하지 않습니다.";
		this.thumbnail = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg";
		this.date = LocalDate.of(9999, 12, 31);
	}

	@Override
	public String toString() {
		return "Posting{" +
			"title='" + title + '\'' +
			", thumbnail='" + thumbnail + '\'' +
			", date=" + date +
			'}';
	}
}
