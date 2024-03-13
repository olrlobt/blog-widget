package olrlobt.githubtistoryposting.domain;

import java.time.LocalDate;

import org.jsoup.nodes.Element;

import lombok.Getter;
import olrlobt.githubtistoryposting.utils.DateUtils;

@Getter
public class Posting {

	private final String title;
	private final String thumbnail;
	private final LocalDate date;

	public Posting(Element element) {
		title = element.select(".tit_post ").text();

		String[] split = element.select("img")
			.attr("src")
			.split("/\\?");

		String[] split1 = split[0].split("C");
		String s = split1[0] + "C217x122";

		thumbnail = "https:" + s + "/?" + split[1];
		date = DateUtils.parser(element.select(".txt_date").text());
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
