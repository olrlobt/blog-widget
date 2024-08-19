package olrlobt.githubtistoryposting.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import olrlobt.githubtistoryposting.domain.BlogInfo;
import olrlobt.githubtistoryposting.domain.Dimensions;
import olrlobt.githubtistoryposting.domain.Posting;
import olrlobt.githubtistoryposting.domain.PostingBase;
import olrlobt.githubtistoryposting.utils.FontUtils;
import olrlobt.githubtistoryposting.utils.SvgUtils;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.svggen.SVGGraphics2D;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

@Service
@Slf4j
public class ImageService {
    private final String TRUNCATE = "...";
    private final Color STROKE_COLOR = Color.decode("#d0d7de");
    //    private final Color STROKE_COLOR = new Color(139, 139, 139, 34);
    @Value("classpath:static/img/No_Image.jpg")
    private Resource NO_IMAGE;
    @Value("classpath:static/img/No_Profile.webp")
    private Resource NO_PROFILE;
    private final OkHttpClient client = new OkHttpClient();

    public byte[] createSvgImageBox(Posting posting) throws IOException {
        SVGGraphics2D svgGenerator = SvgUtils.init();
        PostingBase postingBase = posting.getPostingBase();
        svgGenerator.setSVGCanvasSize(
                new java.awt.Dimension(postingBase.getBox().getWidth(), postingBase.getBox().getHeight()));

        setArcBoxClip(postingBase, svgGenerator);
        drawBackground(svgGenerator, postingBase);
        drawThumbnail(posting, svgGenerator, postingBase);
        svgGenerator.setClip(null);
        drawText(posting, svgGenerator, postingBase);
        drawFooter(posting, svgGenerator, postingBase);
        drawAuthorImg(posting, svgGenerator, postingBase);
        drawAuthorText(posting, svgGenerator, postingBase);
        drawWatermark(posting, svgGenerator, postingBase);
        drawStroke(svgGenerator, postingBase);

        return SvgUtils.toByte(svgGenerator);
    }

    private static void setArcBoxClip(PostingBase postingBase, SVGGraphics2D svgGenerator) {
        Area combinedClip = new Area(new RoundRectangle2D.Double(
                0, 0,
                postingBase.getBox().getWidth(), postingBase.getBox().getHeight(),
                postingBase.getBoxArc().getWidth(), postingBase.getBoxArc().getHeight()
        ));
        svgGenerator.setClip(combinedClip);
    }

    private void drawBackground(SVGGraphics2D svgGenerator, PostingBase postingBase) {
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
            imageUrl = BlogInfo.NOT_FOUND.getBlogThumb();
        }

        Request request = new Request.Builder().url(imageUrl).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to download image: " + response);
            }
            InputStream inputStream = response.body().byteStream();

            BufferedImage originalImage = null;
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);

            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                reader.setInput(imageInputStream);

                String formatName = reader.getFormatName();
                if ("gif".equalsIgnoreCase(formatName)) {
                    // GIF의 첫 프레임만 읽어오기
                    originalImage = reader.read(0);
                } else {
                    // GIF가 아닌 경우 일반적인 방법으로 이미지 읽기
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

                if (originalAspect > targetAspect) { // 원본 이미지 width > height
                    cropHeight = originalHeight;
                    cropWidth = (int) (originalHeight * targetAspect);
                    cropX = (originalWidth - cropWidth) / 2;
                } else { // 원본 이미지 height > width
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
            } else {
                throw new IOException("No suitable ImageReader found for this image format.");
            }
        } catch (IOException e) {
            log.error("Failed to load image from URL: {}", imageUrl, e);
        }
    }

    private void drawText(Posting posting, SVGGraphics2D svgGenerator, PostingBase postingBase) {
        if (postingBase.getTitle().getMaxLine() == -1) {
            return;
        }
        svgGenerator.setPaint(Color.BLACK);
        int titleHeight = drawMultilineText(
                svgGenerator,
                posting.getTitle(),
                postingBase.getTextPadding(),
                postingBase.getTitle().getY(),
                postingBase.getTitle().getWidth() - postingBase.getTextPadding() * 2,
                postingBase.getTitle().getMaxLine(),
                postingBase.getTitle().getWeight() == 1 ?
                        FontUtils.load_b(postingBase.getTitle().getSize())
                        : FontUtils.load_m(postingBase.getTitle().getSize()));

        if (postingBase.getContent().getMaxLine() == -1 || posting.getContent().isEmpty()) {
            return;
        }
        drawMultilineText(
                svgGenerator,
                posting.getContent(),
                postingBase.getTextPadding(),
                titleHeight,
                postingBase.getTitle().getWidth() - postingBase.getTextPadding() * 2,
                postingBase.getContent().getMaxLine(),
                FontUtils.load_m(postingBase.getContent().getSize())
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
            InputStream inputStream = response.body().byteStream();
            BufferedImage originalImage = ImageIO.read(inputStream);

            int imgWidth = postingBase.getBlogImage().getWidth();
            int imgHeight = postingBase.getBlogImage().getHeight();
            int imgX = postingBase.getTextPadding();
            int imgY = postingBase.getBlogImage().getY();

            svgGenerator.setClip(new Ellipse2D.Double(imgX, imgY, imgWidth, imgHeight));
            svgGenerator.drawImage(originalImage, imgX, imgY, imgWidth, imgHeight, null);
            svgGenerator.setClip(null);

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
        if (posting.getWatermark() == null || postingBase.getWatermark().getX() == -1
                || postingBase.getWatermark().getY() == -1) {
            return;
        }

        SVGDocument svgDocument = posting.getWatermark().getSvgDocument();

        if (svgDocument != null) {
            Element svgElement = svgDocument.getDocumentElement();
            changeSVGColor(svgElement, posting.getWatermark().getColor());

            UserAgentAdapter userAgent = new UserAgentAdapter();
            BridgeContext bridgeContext = new BridgeContext(userAgent);
            GraphicsNode svgGraphicsNode = new GVTBuilder().build(bridgeContext, svgDocument);

            AffineTransform scaleTransform = getScaleTransform(svgGraphicsNode,
                    posting.getPostingBase().getWatermark().getWidth(),
                    posting.getPostingBase().getWatermark().getHeight());

            AffineTransform transform = new AffineTransform();
            transform.translate(postingBase.getWatermark().getX(), postingBase.getWatermark().getY());
            transform.concatenate(scaleTransform);

            svgGraphicsNode.setTransform(transform);
            svgGraphicsNode.paint(svgGenerator);
        }
    }


    private void changeSVGColor(Element svgElement, String color) {
        String[] tags = {"circle", "path"};

        for (String tag : tags) {
            NodeList elements = svgElement.getElementsByTagNameNS("*", tag);
            for (int i = 0; i < elements.getLength(); i++) {
                Element element = (Element) elements.item(i);
                element.setAttribute("fill", color);
            }
        }
    }

    private AffineTransform getScaleTransform(GraphicsNode svgGraphicsNode, double targetWidth, double targetHeight) {
        double originalWidth = svgGraphicsNode.getPrimitiveBounds().getWidth();
        double originalHeight = svgGraphicsNode.getPrimitiveBounds().getHeight();
        double scaleX = targetWidth / originalWidth;
        double scaleY = targetHeight / originalHeight;
        return AffineTransform.getScaleInstance(scaleX, scaleY);
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
