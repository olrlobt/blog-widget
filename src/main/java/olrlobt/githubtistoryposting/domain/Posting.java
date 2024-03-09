package olrlobt.githubtistoryposting.domain;

import java.time.LocalDate;
import org.jsoup.nodes.Element;

import olrlobt.githubtistoryposting.utils.DateParser;

public class Posting {

	private final String TITLE;
	private final String THUMBNAIL;
	private final LocalDate DATE;

	public Posting(Element element) {
		TITLE = element.select(".tit_post ").text();
		THUMBNAIL = element.select("img")
			.attr("src")
			.split("=")[1];
		DATE = DateParser.parser(element.select(".txt_date").text());
	}

	@Override
	public String toString() {
		return "Posting{" +
			"title='" + TITLE + '\'' +
			", thumbnail='" + THUMBNAIL + '\'' +
			", date=" + DATE +
			'}';
	}
}
