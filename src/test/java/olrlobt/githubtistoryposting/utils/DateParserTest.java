package olrlobt.githubtistoryposting.utils;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class DateParserTest {

	private static List<String> dateInputs() {
		return Arrays.asList(
			"2024. 3. 17. 14:30",
			"2024.3.17",
			"2024/3/17"
		);
	}

	@ParameterizedTest
	@MethodSource("dateInputs")
	void parser(String value) {
		LocalDate result = DateParser.parser(value);
		assertThat(result).isEqualTo(LocalDate.of(2024, 3, 17));
	}
}