package olrlobt.githubtistoryposting.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TextUtils {

	private static final String ELLIPSIS = "...";

	public static List<String> wrap(Graphics2D g2d, String text, int maxWidth, int maxLine) {
		FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
		List<String> lines = new ArrayList<>();
		String currentLine = "";
		int lineCount = 0;

		for (int i = 0; i < text.length(); ) {
			if (lineCount == maxLine) {
				String trimmedLine = currentLine + ELLIPSIS;
				if (metrics.stringWidth(trimmedLine) > maxWidth) {
					int trimPoint = currentLine.length();
					while (trimPoint > 0
						&& metrics.stringWidth(currentLine.substring(0, trimPoint) + ELLIPSIS) > maxWidth) {
						trimPoint--;
					}
					lines.add(currentLine.substring(0, trimPoint) + ELLIPSIS);
				} else {
					lines.add(trimmedLine);
				}
				break;
			}

			int breakPoint = i;
			for (int j = i + 1; j <= text.length(); j++) {
				String subStr = text.substring(i, j);
				if (metrics.stringWidth(currentLine + subStr) > maxWidth) {
					break;
				}
				breakPoint = j;
			}

			if (breakPoint == i) {
				while (i < text.length() && metrics.stringWidth(currentLine + text.charAt(i)) <= maxWidth) {
					currentLine += text.charAt(i++);
				}
				lines.add(currentLine);
				currentLine = "";
				lineCount++;
			} else {
				String wordToAdd = text.substring(i, breakPoint);
				if (currentLine.isEmpty()) {
					currentLine = wordToAdd;
				} else {
					currentLine += " " + wordToAdd;
				}
				i = breakPoint;
			}

			if (i < text.length() && breakPoint == text.length()) {
				lines.add(currentLine);
				lineCount++;
			}
		}

		if (!currentLine.isEmpty() && lineCount < maxLine) {
			lines.add(currentLine);
		}
		return lines;
	}
}
