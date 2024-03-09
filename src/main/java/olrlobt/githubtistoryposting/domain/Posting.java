package olrlobt.githubtistoryposting.domain;

import java.time.LocalDate;
import org.jsoup.nodes.Element;

import lombok.Getter;
import olrlobt.githubtistoryposting.utils.DateParser;

@Getter
public class Posting {

	private final String title;
	private final String thumbnail;
	private final LocalDate date;

	public Posting(Element element) {
		title = element.select(".tit_post ").text();
		thumbnail = element.select("img")
			.attr("src")
			.split("=")[1];
		date = DateParser.parser(element.select(".txt_date").text());
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
