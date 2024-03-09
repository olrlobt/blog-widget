package olrlobt.githubtistoryposting.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.domain.Postings;
import olrlobt.githubtistoryposting.utils.CreateImgFile;

@Service
public class ImageService {

	private final int BOX_WIDTH = 217; 
	private final int BOX_HEIGHT = 126; 
	private final int TOTAL_HEIGHT = 260;

	public void getImageBox(Postings postings) throws IOException {
		int count = 1;
		for (Posting posting : postings.getPostings()) {
			File tempImg = CreateImgFile.fromUrl(posting.getThumbnail());
			makeImageBox(tempImg, count++);
		}
	}

	private void makeImageBox(File tempImg, int count) throws IOException {
		File outputDir = new File("src/main/resources/static/img/");
		outputDir.mkdirs();
		File outputFile = new File(outputDir, count + "generated_image.png");

		BufferedImage originalImage = ImageIO.read(tempImg);
		BufferedImage imageBox = new BufferedImage(BOX_WIDTH, TOTAL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		drawBox(imageBox, originalImage);

		ImageIO.write(imageBox, "png", outputFile);
	}

	private void drawBox(BufferedImage imageBox, BufferedImage originalImage) {
		Graphics2D g2d = imageBox.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, BOX_WIDTH, TOTAL_HEIGHT);
		g2d.drawImage(resizeThumb(originalImage), 0, 0, null);
	}

	private BufferedImage resizeThumb(BufferedImage originalImage) {
		double originalAspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();
		double boxAspectRatio = (double) BOX_WIDTH / BOX_HEIGHT;

		int targetWidth = originalAspectRatio > boxAspectRatio ? (int) (BOX_HEIGHT * originalAspectRatio) : BOX_WIDTH;
		int targetHeight = originalAspectRatio <= boxAspectRatio ? (int) (BOX_WIDTH / originalAspectRatio) : BOX_HEIGHT;

		BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
		g2d.dispose();

		int cropStartX = (targetWidth - BOX_WIDTH) / 2;
		int cropStartY = (targetHeight - BOX_HEIGHT) / 2;

		return bufferedImage.getSubimage(cropStartX, cropStartY, BOX_WIDTH, BOX_HEIGHT);
	}
}
