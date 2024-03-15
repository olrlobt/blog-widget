package olrlobt.githubtistoryposting.domain;

import java.time.LocalDate;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import olrlobt.githubtistoryposting.utils.DateUtils;

@Getter
@AllArgsConstructor
public class Posting {

	private final String thumbnail;
	private final String title;
	private final String footer;

	public Posting(Element element) {
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

		title = element.select(".tit_post ").text();
		LocalDate parser = DateUtils.parser(element.select(".txt_date").text());
		footer = DateUtils.toString(parser);
	}

	public Posting() {
		this.thumbnail = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg";
		this.title = "포스팅이 존재하지 않습니다.";
		this.footer = "-";
	}

	public static Posting createThumbnailBox(Elements element){
		// C428x428
		String thumbnail = element.select("meta[property=og:image]")
			.attr("content");
		String title = element.select("meta[property=og:site_name]")
			.attr("content");
		String footer = element.select("meta[property=og:url]")
			.attr("content");
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
