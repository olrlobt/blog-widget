package olrlobt.githubtistoryposting.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import olrlobt.githubtistoryposting.domain.BlogInfo;
import olrlobt.githubtistoryposting.domain.Dimensions;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.domain.PostingBase;
import olrlobt.githubtistoryposting.domain.TextDimensions;
import olrlobt.githubtistoryposting.utils.FontUtils;
import olrlobt.githubtistoryposting.utils.SvgPool;
import org.apache.batik.svggen.SVGGraphics2D;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private static final String TRUNCATE = "...";
    private static final Color STROKE_COLOR = Color.decode("#d0d7de");
    //    private final Color STROKE_COLOR = new Color(139, 139, 139, 34);
    @Value("classpath:static/img/No_Image.jpg")
    private Resource NO_IMAGE;
    @Value("classpath:static/img/No_Profile.webp")
    private Resource NO_PROFILE;
    private final OkHttpClient client = new OkHttpClient();
    private final SvgPool svgPool;

    public byte[] createSvgImageBox(Posting posting) {
        SVGGraphics2D svgGenerator = null;

        try {
            svgGenerator = svgPool.borrowObject();
            final SVGGraphics2D finalSvgGenerator = svgGenerator;
            PostingBase postingBase = posting.getPostingBase();
            final PostingBase finalPostingBase = postingBase;

            finalSvgGenerator.setSVGCanvasSize(
                    new java.awt.Dimension(finalPostingBase.getBox().getWidth(), finalPostingBase.getBox().getHeight()));

            CompletableFuture<Void> backgroundTask = CompletableFuture.runAsync(() -> drawBackground(finalSvgGenerator, finalPostingBase));
            CompletableFuture<Void> thumbnailTask = CompletableFuture.runAsync(() -> drawThumbnail(posting, finalSvgGenerator, finalPostingBase));
            CompletableFuture<Void> textTask = backgroundTask.thenRunAsync(() -> drawText(posting, finalSvgGenerator, finalPostingBase));
            CompletableFuture<Void> footerTask = textTask.thenRunAsync(() -> drawFooter(posting, finalSvgGenerator, finalPostingBase));
            CompletableFuture<Void> authorImgTask = backgroundTask.thenRunAsync(() -> drawAuthorImg(posting, finalSvgGenerator, finalPostingBase));
            CompletableFuture<Void> authorTextTask = footerTask.thenRunAsync(() -> drawAuthorText(posting, finalSvgGenerator, finalPostingBase));
            CompletableFuture<Void> watermarkTask = CompletableFuture.runAsync(() -> drawWatermark(posting, finalSvgGenerator, finalPostingBase));
            CompletableFuture<Void> strokeTask = CompletableFuture.allOf(thumbnailTask, authorTextTask)
                    .thenRunAsync(() -> drawStroke(finalSvgGenerator, finalPostingBase));

            CompletableFuture<Void> allTasks = CompletableFuture.allOf(
                    backgroundTask, thumbnailTask, textTask, footerTask, authorImgTask, authorTextTask, watermarkTask, strokeTask);

            allTasks.get();
            return svgPool.toByte(finalSvgGenerator);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            svgPool.returnObject(svgGenerator);
        }
    }

    private void setArcBoxClip(PostingBase postingBase, SVGGraphics2D svgGenerator) {
        Area combinedClip = new Area(new RoundRectangle2D.Double(
                0, 0,
                postingBase.getBox().getWidth(), postingBase.getBox().getHeight(),
                postingBase.getBoxArc().getWidth(), postingBase.getBoxArc().getHeight()
        ));
        svgGenerator.setClip(combinedClip);
    }

    private void drawBackground(SVGGraphics2D svgGenerator, PostingBase postingBase) {
        setArcBoxClip(postingBase, svgGenerator);
        svgGenerator.setPaint(Color.WHITE);
        svgGenerator.fill(
                new Rectangle2D.Double(0, 0, postingBase.getBox().getWidth(), postingBase.getBox().getHeight()));
    }

    private void drawThumbnail(Posting posting, SVGGraphics2D svgGenerator, PostingBase postingBase) {
        if (postingBase.getImg() == Dimensions.EMPTY) {
            return;
        }
        String imageUrl = posting.getThumbnail();
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }

        Request request = new Request.Builder().url(imageUrl).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to download image: " + response);
            }

            try (InputStream inputStream = new BufferedInputStream(response.body().byteStream())) {
                BufferedImage originalImage = null;
                ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
                Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
                if (!readers.hasNext()) {
                    throw new IOException("No suitable ImageReader found for this image format.");
                }

                ImageReader reader = readers.next();
                reader.setInput(imageInputStream);

                String formatName = reader.getFormatName();
                if ("gif".equalsIgnoreCase(formatName)) {
                    originalImage = reader.read(0);
                } else {
                    originalImage = ImageIO.read(imageInputStream);
                }

                int originalWidth = originalImage.getWidth();
                int originalHeight = originalImage.getHeight();

                int targetWidth = postingBase.getImg().getWidth();
                int targetHeight = postingBase.getImg().getHeight();
                int targetX = postingBase.getImg().getX();
                int targetY = 0;
                double originalAspect = (double) originalWidth / originalHeight;
                double targetAspect = (double) targetWidth / targetHeight;
                int cropX = 0, cropY = 0, cropWidth, cropHeight;

                if (originalAspect > targetAspect) {
                    cropHeight = originalHeight;
                    cropWidth = (int) (originalHeight * targetAspect);
                    cropX = (originalWidth - cropWidth) / 2;
                } else {
                    cropWidth = originalWidth;
                    cropHeight = (int) (originalWidth / targetAspect);
                    cropY = (originalHeight - cropHeight) / 2;
                }

                BufferedImage croppedImage = originalImage.getSubimage(cropX, cropY, cropWidth, cropHeight);
                svgGenerator.drawImage(croppedImage,
                        targetX,
                        targetY,
                        targetWidth,
                        targetHeight,
                        null);
            }
        } catch (IOException e) {
            log.error("Failed to load image from URL: {}", imageUrl, e);
        }
    }

    private void drawText(Posting posting, SVGGraphics2D svgGenerator, PostingBase postingBase) {
        if (postingBase.getTitle().getMaxLine() == -1) {
            return;
        }

        TextDimensions title = postingBase.getTitle();
        if (posting.getThumbnail() == null) {
            title = postingBase.getNoThumbTitle();
        }

        svgGenerator.setPaint(Color.BLACK);
        int titleHeight = drawMultilineText(
                svgGenerator,
                posting.getTitle(),
                postingBase.getTextPadding(),
                title.getY(),
                title.getWidth() - postingBase.getTextPadding() * 2,
                title.getMaxLine(),
                title.getWeight() == 1 ?
                        FontUtils.load_b(title.getSize())
                        : FontUtils.load_m(title.getSize()));

        if (postingBase.getContent() == TextDimensions.EMPTY || posting.getContent().isEmpty()) {
            return;
        }
        TextDimensions content = postingBase.getContent();
        if (posting.getThumbnail() == null) {
            content = postingBase.getNoThumbContent();
        }

        drawMultilineText(
                svgGenerator,
                posting.getContent(),
                postingBase.getTextPadding(),
                titleHeight,
                title.getWidth() - postingBase.getTextPadding() * 2,
                content.getMaxLine(),
                FontUtils.load_m(content.getSize())
        );
    }

    private static void drawFooter(Posting posting, SVGGraphics2D svgGenerator, PostingBase postingBase) {
        int footerType = postingBase.getFooterType();
        if (footerType == -1) {
            return;
        }
        svgGenerator.setFont(FontUtils.load_m());
        svgGenerator.setPaint(Color.GRAY);

        String publishedTime = posting.getPublishedTime();
        String url = posting.getUrl();
        String footer;
        int height;

        if (footerType == 0) {
            footer = publishedTime;
            svgGenerator.drawString(footer, postingBase.getTextPadding(), postingBase.getPublishedTimeY());
            svgGenerator.setFont(FontUtils.load_b());
            footer = posting.getSiteName();
            height = postingBase.getUrlY();
        } else {
            boolean usePublishedTime = (footerType == 1 && publishedTime != null && !publishedTime.isEmpty())
                    || (footerType == 2 && (url == null || url.isEmpty()));
            footer = usePublishedTime ? publishedTime : url;
            height = usePublishedTime ? postingBase.getPublishedTimeY() : postingBase.getUrlY();
        }
        svgGenerator.drawString(footer, postingBase.getTextPadding(), height);
    }

    private void drawAuthorImg(Posting posting, SVGGraphics2D svgGenerator, PostingBase postingBase) {
        if (postingBase.getBlogImage() == Dimensions.EMPTY) {
            return;
        }
        String imageUrl = posting.getBlogImage();

        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = BlogInfo.NOT_FOUND.getBlogThumb();
        }

        Request request = new Request.Builder().url(imageUrl).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to download image: " + response);
            }

            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new IOException("Response body is null: " + response);
            }

            try (InputStream inputStream = new BufferedInputStream(responseBody.byteStream());
                 ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream)) {

                Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
                if (!readers.hasNext()) {
                    throw new IOException("No suitable ImageReader found for this image format.");
                }

                ImageReader reader = readers.next();
                try {
                    reader.setInput(imageInputStream);

                    int imgWidth = postingBase.getBlogImage().getWidth();
                    int imgHeight = postingBase.getBlogImage().getHeight();
                    int imgX = postingBase.getTextPadding();
                    int imgY = postingBase.getBlogImage().getY();
                    int originalWidth = reader.getWidth(0);
                    int originalHeight = reader.getHeight(0);

                    // 서브샘플링 비율 계산
                    int subsampleX = originalWidth / imgWidth;
                    int subsampleY = originalHeight / imgHeight;
                    int subsample = Math.max(1, Math.min(subsampleX, subsampleY));

                    ImageReadParam param = reader.getDefaultReadParam();
                    param.setSourceSubsampling(subsample, subsample, 0, 0);

                    BufferedImage originalImage = reader.read(0, param);

                    svgGenerator.setClip(new Ellipse2D.Double(imgX, imgY, imgWidth, imgHeight));
                    svgGenerator.drawImage(originalImage, imgX, imgY, imgWidth, imgHeight, null);
                    svgGenerator.setClip(null);

                } finally {
                    reader.dispose();
                }
            }
        } catch (IOException e) {
            log.error("Failed to load image from URL: {}", imageUrl, e);
        }
    }

    private void drawAuthorText(Posting posting, SVGGraphics2D svgGenerator, PostingBase postingBase) {
        if (postingBase.getBlogImage().getY() == -1) {
            return;
        }

        drawStroke(svgGenerator, 0, postingBase.getBox().getWidth(),
                postingBase.getBlogImage().getY() - postingBase.getTextPadding() / 2,
                postingBase.getBlogImage().getY() - postingBase.getTextPadding() / 2 + 1);

        svgGenerator.setFont(FontUtils.load_m());

        String byText = "by ";
        drawText(svgGenerator, byText,
                postingBase.getTextPadding() + postingBase.getBlogImage().getWidth() * 3 / 2
                , postingBase.getBlogImage().getY() + postingBase.getBlogImage().getHeight() * 2 / 3,
                Color.GRAY);

        FontMetrics metrics = svgGenerator.getFontMetrics();
        drawText(svgGenerator, posting.getAuthor(),
                postingBase.getTextPadding() + postingBase.getBlogImage().getWidth() * 3 / 2 + metrics.stringWidth(
                        byText)
                , postingBase.getBlogImage().getY() + postingBase.getBlogImage().getHeight() * 2 / 3,
                Color.BLACK);
    }

    private static void drawText(SVGGraphics2D svgGenerator, String text, int x, int y, Color color) {
        svgGenerator.setPaint(color);
        svgGenerator.drawString(text, x, y);
    }

    private void drawWatermark(Posting posting, SVGGraphics2D svgGenerator, PostingBase postingBase) {
        if (postingBase.getWatermark().getX() == -1 || postingBase.getWatermark().getY() == -1
        || posting.getWatermark() == null) {
            return;
        }
        BufferedImage watermarkImage = posting.getWatermark().getBufferedImage();
        int x = postingBase.getWatermark().getX();
        int y = postingBase.getWatermark().getY();
        int width = postingBase.getWatermark().getWidth();
        int height = postingBase.getWatermark().getHeight();

        svgGenerator.drawImage(watermarkImage, x, y, width, height, null);
    }

    private void drawStroke(SVGGraphics2D svgGenerator, PostingBase postingBase) {
        svgGenerator.setPaint(STROKE_COLOR);
        RoundRectangle2D stroke = new RoundRectangle2D.Double(
                0, 0,
                postingBase.getBox().getWidth(), postingBase.getBox().getHeight(),
                postingBase.getBoxArc().getWidth(), postingBase.getBoxArc().getHeight()
        );
        svgGenerator.draw(stroke);
    }

    private void drawStroke(SVGGraphics2D svgGenerator, int startX, int endX, int startY, int endY) {
        svgGenerator.setPaint(STROKE_COLOR);
        svgGenerator.draw(new Line2D.Double(startX, startY, endX, endY));
    }

    public int drawMultilineText(SVGGraphics2D svgGenerator, String text,
                                 int startX, int startY, int maxWidth, int maxLines, Font font) {
        FontMetrics metrics = svgGenerator.getFontMetrics(FontUtils.load_size(font.getSize()));
        int lineHeight = metrics.getHeight();
        svgGenerator.setFont(font);

        int linesCount = 0;
        StringBuilder currentLine = new StringBuilder();
        for (char ch : text.toCharArray()) {
            currentLine.append(ch);
            String lineText = currentLine.toString();
            double lineWidth = metrics.stringWidth(lineText) + startX;
            if (lineWidth > maxWidth || text.indexOf(ch) == text.length() - 1) {
                if (linesCount < maxLines - 1) {
                    svgGenerator.drawString(lineText, startX, startY + linesCount * lineHeight);
                    linesCount++;
                    currentLine.setLength(0);
                } else {
                    if (lineWidth > maxWidth) {
                        while (metrics.stringWidth(lineText + TRUNCATE) > maxWidth && !lineText.isEmpty()) {
                            lineText = lineText.substring(0, lineText.length() - 1);
                        }
                        lineText += TRUNCATE;
                    }
                    svgGenerator.drawString(lineText, startX, startY + linesCount * lineHeight);
                    linesCount++;
                    break;
                }
            }
        }

        if (!currentLine.isEmpty() && linesCount < maxLines) {
            svgGenerator.drawString(currentLine.toString(), startX, startY + linesCount * lineHeight);
            linesCount++;
        }
        return startY + linesCount * lineHeight;
    }
}
