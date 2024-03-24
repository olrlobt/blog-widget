package olrlobt.githubtistoryposting.service;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.batik.svggen.SVGGraphics2D;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.BlogInfo;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.domain.PostingType;
import olrlobt.githubtistoryposting.utils.FontUtils;
import olrlobt.githubtistoryposting.utils.SvgUtils;

@Service
@Slf4j
public class ImageService {

	private final int BOX_WIDTH = 217;
	private final int BOX_HEIGHT = 126;
	private final int TOTAL_HEIGHT = 260;
	private final int PADDING = 20;
	private final int PADDING_TOP = 25;
	private final int MAX_LINES = 3;
	private final String TRUNCATE = "...";
	private final Color STROKE_COLOR = Color.decode("#d0d7de");

	public byte[] createSvgImageBox(Posting posting) throws IOException {
		SVGGraphics2D svgGenerator = SvgUtils.init();
		svgGenerator.setSVGCanvasSize(new java.awt.Dimension(BOX_WIDTH, TOTAL_HEIGHT));
		drawBackground(svgGenerator);
		drawThumbnail(posting, svgGenerator, posting.getPostingType());
		drawText(posting, svgGenerator, posting.getPostingType());
		drawStroke(svgGenerator);

		return SvgUtils.toByte(svgGenerator);
	}

	private void drawBackground(SVGGraphics2D svgGenerator) {
		svgGenerator.setPaint(Color.WHITE);
		svgGenerator.fill(new Rectangle2D.Double(0, 0, BOX_WIDTH, TOTAL_HEIGHT));
	}

	private void drawThumbnail(Posting posting, SVGGraphics2D svgGenerator, PostingType postingType) throws
		IOException {
		BufferedImage originalImage;
		if (posting.getThumbnail() != null && !posting.getThumbnail().isEmpty()) {
			try {
				originalImage = ImageIO.read(new URL(posting.getThumbnail()));
				svgGenerator.drawImage(originalImage, 0, 0, postingType.getWidth(), postingType.getHeight(), null);
			} catch (IOException ignored){
				log.error("요청 URL = {}", posting.getThumbnail());
			}
		}else {
			originalImage = ImageIO.read(new URL(BlogInfo.NOT_FIND.getBlogThumb()));
			svgGenerator.drawImage(originalImage, 0, 0, postingType.getWidth(), postingType.getHeight(), null);
		}
	}

	private void drawText(Posting posting, SVGGraphics2D svgGenerator, PostingType postingType) {
		svgGenerator.setPaint(Color.BLACK);
		if (postingType.getStartText() >= 0) {
			drawMultilineText(svgGenerator, posting.getTitle(), PADDING, postingType.getStartText() + PADDING_TOP, postingType.getWidth() - PADDING * 2,
				MAX_LINES);
		}
		svgGenerator.setPaint(Color.GRAY);
		svgGenerator.drawString(posting.getFooter(), PADDING, TOTAL_HEIGHT - PADDING);
	}

	private void drawStroke(SVGGraphics2D svgGenerator) {
		svgGenerator.setPaint(STROKE_COLOR);
		svgGenerator.draw(new Rectangle2D.Double(0, 0, BOX_WIDTH - 1, TOTAL_HEIGHT - 1));
	}

	public void drawMultilineText(SVGGraphics2D svgGenerator, String text, int x, int y, int maxWidth, int maxLines) {
		Font font = FontUtils.load_b();
		svgGenerator.setFont(font);
		FontMetrics metrics = svgGenerator.getFontMetrics(font);

		int lineHeight = metrics.getHeight();
		int linesCount = 0;
		StringBuilder currentLine = new StringBuilder();

		for (char ch : text.toCharArray()) {
			currentLine.append(ch);
			String lineText = currentLine.toString();
			double lineWidth = metrics.stringWidth(lineText);

			if (lineWidth > maxWidth || text.indexOf(ch) == text.length() - 1) {
				if (lineWidth <= maxWidth && linesCount < maxLines - 1) {
					svgGenerator.drawString(lineText, x, y + linesCount * lineHeight);
					return;
				}

				if (linesCount < maxLines) {
					String toDraw = lineWidth > maxWidth ? lineText.substring(0, lineText.length() - 1) : lineText;
					svgGenerator.drawString(toDraw, x, y + linesCount * lineHeight);
					currentLine = lineWidth > maxWidth ? new StringBuilder("" + ch) : new StringBuilder();
					linesCount++;
				} else {
					svgGenerator.drawString(TRUNCATE, x, y + linesCount * lineHeight);
					return;
				}
			}
		}

		if (currentLine.length() > 0 && linesCount < maxLines) {
			svgGenerator.drawString(currentLine.toString(), x, y + linesCount * lineHeight);
		}
	}
}
