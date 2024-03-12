package olrlobt.githubtistoryposting.service;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.batik.svggen.SVGGraphics2D;
import org.springframework.stereotype.Service;

import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.utils.FontUtils;
import olrlobt.githubtistoryposting.utils.SvgUtils;

@Service
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
		drawThumbnail(posting, svgGenerator);
		drawText(posting, svgGenerator);
		drawStroke(svgGenerator);

		return SvgUtils.toByte(svgGenerator);
	}

	private void drawBackground(SVGGraphics2D svgGenerator) {
		svgGenerator.setPaint(Color.WHITE);
		svgGenerator.fill(new Rectangle2D.Double(0, 0, BOX_WIDTH, TOTAL_HEIGHT));
	}

	private void drawThumbnail(Posting posting, SVGGraphics2D svgGenerator) throws IOException {
		BufferedImage originalImage = ImageIO.read(new URL(posting.getThumbnail()));
		BufferedImage resizedImage = resizeThumb(originalImage);
		svgGenerator.drawImage(resizedImage, 0, 0, BOX_WIDTH, BOX_HEIGHT, null);
	}

	private void drawText(Posting posting, SVGGraphics2D svgGenerator) {
		svgGenerator.setPaint(Color.BLACK);
		drawMultilineText(svgGenerator, posting.getTitle(), PADDING, BOX_HEIGHT + PADDING_TOP, BOX_WIDTH - PADDING * 2,
			MAX_LINES);
		svgGenerator.setPaint(Color.GRAY);
		svgGenerator.drawString(posting.getDate().toString(), PADDING, TOTAL_HEIGHT - PADDING);
	}

	private void drawStroke(SVGGraphics2D svgGenerator) {
		svgGenerator.setPaint(STROKE_COLOR);
		svgGenerator.draw(new Rectangle2D.Double(0, 0, BOX_WIDTH - 1, TOTAL_HEIGHT - 1));
	}

	private BufferedImage resizeThumb(BufferedImage originalImage) {
		double originalAspectRatio = (double)originalImage.getWidth() / originalImage.getHeight();
		double boxAspectRatio = (double)BOX_WIDTH / BOX_HEIGHT;

		int targetWidth = originalAspectRatio > boxAspectRatio ? (int)(BOX_HEIGHT * originalAspectRatio) : BOX_WIDTH;
		int targetHeight = originalAspectRatio <= boxAspectRatio ? (int)(BOX_WIDTH / originalAspectRatio) : BOX_HEIGHT;

		BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
		g2d.dispose();

		int cropStartX = (targetWidth - BOX_WIDTH) / 2;
		int cropStartY = (targetHeight - BOX_HEIGHT) / 2;

		return bufferedImage.getSubimage(cropStartX, cropStartY, BOX_WIDTH, BOX_HEIGHT);
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
