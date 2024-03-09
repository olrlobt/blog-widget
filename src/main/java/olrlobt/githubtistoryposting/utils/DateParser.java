package olrlobt.githubtistoryposting.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateParser {

	public static LocalDate parser(String txtDate){
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			.appendOptional(DateTimeFormatter.ofPattern("yyyy. M. d. HH:mm"))
			.appendOptional(DateTimeFormatter.ofPattern("yyyy.M.d"))
			.appendOptional(DateTimeFormatter.ofPattern("yyyy/M/d"))
			.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
			.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
			.toFormatter();

		try {
			TemporalAccessor ta = formatter.parseBest(txtDate, LocalDateTime::from, LocalDate::from);
			if (ta instanceof LocalDateTime) {
				return ((LocalDateTime) ta).toLocalDate();
			} else if (ta instanceof LocalDate) {
				return (LocalDate) ta;
			}
		} catch (Exception e) {
			log.error("날짜 파싱 에러: {}", txtDate);
		}
		return null;
	}
}
