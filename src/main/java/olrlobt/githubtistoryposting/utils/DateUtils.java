package olrlobt.githubtistoryposting.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtils {

	private static final DateTimeFormatter PARSER_FORMATTER = new DateTimeFormatterBuilder()
			.appendOptional(DateTimeFormatter.ofPattern("yyyy. M. d. HH:mm"))
			.appendOptional(DateTimeFormatter.ofPattern("yyyy. M. d."))
			.appendOptional(DateTimeFormatter.ofPattern("yyyy.M.d"))
			.appendOptional(DateTimeFormatter.ofPattern("yyyy/M/d"))
			.appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX"))
			.appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssZ"))
			.appendOptional(DateTimeFormatter.ISO_INSTANT) // velog
			.appendOptional(DateTimeFormatter.ISO_ZONED_DATE_TIME) // anything else
			.appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
			.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
			.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
			.toFormatter();

	public static LocalDate parser(String txtDate) {
		if (txtDate.isEmpty() || txtDate.isBlank()) {
			return null;
		}

		try {
			TemporalAccessor ta = PARSER_FORMATTER.parseBest(txtDate, ZonedDateTime::from, Instant::from,
					LocalDate::from, LocalDateTime::from);

			if (ta instanceof Instant) {
				ZonedDateTime zdt = ((Instant)ta).atZone(ZoneId.systemDefault());
				return zdt.toLocalDate();
			} else if (ta instanceof ZonedDateTime) {
				return ((ZonedDateTime) ta).toLocalDate();
			} else if (ta instanceof LocalDateTime) {
				return ((LocalDateTime)ta).toLocalDate();
			} else if (ta instanceof LocalDate) {
				return (LocalDate)ta;
			}
		} catch (Exception e) {
			log.error("날짜 파싱 에러: {}", txtDate);
		}
		return null;
	}

	public static String toString(LocalDate localDate) {
		if (localDate == null) {
			return null;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		try {
			return localDate.format(formatter);
		} catch (Exception ignored) {
			log.error("날짜 파싱 에러: {}", localDate);
			return "";
		}
	}
}
