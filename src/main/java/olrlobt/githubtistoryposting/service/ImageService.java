package olrlobt.githubtistoryposting.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.domain.Postings;
import olrlobt.githubtistoryposting.utils.CreateImgFile;
import olrlobt.githubtistoryposting.utils.TextUtils;

@Service
public class ImageService {

	private final int BOX_WIDTH = 217;
	private final int BOX_HEIGHT = 126;
	private final int TOTAL_HEIGHT = 260;

	public void getImageBox(Postings postings) throws IOException {
		int count = 1;
		for (Posting posting : postings.getPostings()) {
			File tempImg = CreateImgFile.fromUrl(posting.getThumbnail());
			makeImageBox(tempImg, count++, posting);
		}
	}

	private void makeImageBox(File tempImg, int count, Posting posting) throws IOException {
		File outputDir = new File("src/main/resources/static/img/");
		outputDir.mkdirs();
		File outputFile = new File(outputDir, count + "generated_image.png");

		BufferedImage originalImage = ImageIO.read(tempImg);
		BufferedImage imageBox = new BufferedImage(BOX_WIDTH, TOTAL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = imageBox.createGraphics();

		drawThumbnail(graphics, originalImage);
		drawStroke(graphics);
		drawTitle(graphics, posting.getTitle());
		drawDate(graphics, posting.getDate());

		graphics.dispose();
		ImageIO.write(imageBox, "png", outputFile);
	}

	private void drawStroke(Graphics2D graphics) {
		graphics.setColor(Color.decode("#d0d7de"));
		Stroke oldStroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(1));
		graphics.drawRect(0, 0, BOX_WIDTH - 1, TOTAL_HEIGHT - 1);
		graphics.setStroke(oldStroke);
	}

	private void drawDate(Graphics2D graphics, LocalDate date) {
		String createdAt = TextUtils.ofLocalDate(date);
		graphics.setColor(Color.GRAY);
		graphics.setFont(new Font("Noto Sans Regular", Font.PLAIN, 11));

		graphics.drawString(createdAt, 25, TOTAL_HEIGHT - 20);
	}

	private void drawTitle(Graphics2D graphics, String title) {
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Noto Sans Regular", Font.BOLD, 14));

		List<String> wrappedText = TextUtils.wrap(graphics, title, 167, 3);
		int lineHeight = graphics.getFontMetrics().getHeight();
		for (int i = 0; i < wrappedText.size(); i++) {
			graphics.drawString(wrappedText.get(i), 25, BOX_HEIGHT + 30 + (i * lineHeight));
		}
	}

	private void drawThumbnail(Graphics2D graphics, BufferedImage originalImage) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, BOX_WIDTH, TOTAL_HEIGHT);
		graphics.drawImage(resizeThumb(originalImage, BOX_WIDTH, BOX_HEIGHT), 0, 0, null);
	}

	private BufferedImage resizeThumb(BufferedImage originalImage, int BOX_WIDTH, int BOX_HEIGHT) {
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
}
