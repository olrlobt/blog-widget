package olrlobt.githubtistoryposting.service;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import olrlobt.githubtistoryposting.domain.BlogInfo;
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

    public byte[] createSvgImageBox(Posting posting) throws IOException {
        SVGGraphics2D svgGenerator = SvgUtils.init();
        PostingBase postingBase = posting.getPostingBase();
        svgGenerator.setSVGCanvasSize(new java.awt.Dimension(postingBase.getBoxWidth(), postingBase.getBoxHeight()));
        drawBackground(svgGenerator, postingBase);
        drawThumbnail(posting, svgGenerator, postingBase);
        drawText(posting, svgGenerator, postingBase);
        drawFooter(posting, svgGenerator, postingBase);
        drawAuthorImg(posting, svgGenerator, postingBase);
        drawAuthorText(posting, svgGenerator, postingBase);
        drawWatermark(posting, svgGenerator, postingBase);
        drawStroke(svgGenerator, postingBase);

        return SvgUtils.toByte(svgGenerator);
    }

    private void drawBackground(SVGGraphics2D svgGenerator, PostingBase postingBase) {
        svgGenerator.setPaint(Color.WHITE);

        RoundRectangle2D background = new RoundRectangle2D.Double(
                0, 0,
                postingBase.getBoxWidth(), postingBase.getBoxHeight(),
                postingBase.getBoxArcWidth(), postingBase.getBoxArcHeight()
        );
        svgGenerator.fill(background);
//        svgGenerator.fill(new Rectangle2D.Double(0, 0, postingType.getBoxWidth(), postingType.getBoxHeight()));
    }

    private void drawThumbnail(Posting posting, SVGGraphics2D svgGenerator, PostingBase postingBase) {
        String imageUrl = posting.getThumbnail();
        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = BlogInfo.NOT_FOUND.getBlogThumb();
        }

        try {
            BufferedImage originalImage = ImageIO.read(new URL(imageUrl));
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            int targetWidth = postingBase.getImgWidth();
            int targetHeight = postingBase.getImgHeight();
            int targetX = postingBase.getImgX();
            int targetY = 0;
            double originalAspect = (double) originalWidth / originalHeight;
            double targetAspect = (double) targetWidth / targetHeight;

            int drawWidth, drawHeight;
            int xOffset = 0, yOffset = 0;

            if (originalAspect > targetAspect) { // 원본 이미지 width > height
                drawHeight = targetHeight;
                drawWidth = (int) (targetHeight * originalAspect);
                xOffset = (drawWidth - targetWidth) / 2;
            } else { // 원본 이미지 height > width
                drawWidth = targetWidth;
                drawHeight = (int) (targetWidth / originalAspect);
                yOffset = (drawHeight - targetHeight) / 2;
            }

            Area combinedClip = new Area(new RoundRectangle2D.Double(
                    0, 0, postingBase.getBoxWidth(), postingBase.getBoxHeight(), postingBase.getBoxArcWidth(),
                    postingBase.getBoxArcHeight()
            ));
            combinedClip.intersect(new Area(new Rectangle2D.Double(
                    targetX, targetY, targetWidth, targetHeight
            )));

            Shape originalClip = svgGenerator.getClip();
            svgGenerator.setClip(combinedClip);

            svgGenerator.drawImage(originalImage,
                    targetX - xOffset,
                    targetY - yOffset,
                    drawWidth,
                    drawHeight,
                    null);

            svgGenerator.setClip(originalClip);

        } catch (IOException e) {
            log.error("Failed to load image from URL: {}", imageUrl, e);
        }
    }

    private void drawText(Posting posting, SVGGraphics2D svgGenerator, PostingBase postingBase) {
        if (postingBase.getTitleMaxLine() == -1) {
            return;
        }
        svgGenerator.setPaint(Color.BLACK);
        int titleHeight = drawMultilineText(
                svgGenerator,
                posting.getTitle(),
                postingBase.getTextPadding(),
                postingBase.getTitleY(),
                postingBase.getTitleWidth() - postingBase.getTextPadding() * 2,
                postingBase.getTitleMaxLine(),
                postingBase.getTitleWeight() == 1 ?
                        FontUtils.load_b(postingBase.getTitleSize()) : FontUtils.load_m(postingBase.getTitleSize()
                ));

        if (postingBase.getContentMaxLine() == -1 || posting.getContent().isEmpty()) {
            return;
        }
        drawMultilineText(
                svgGenerator,
                posting.getContent(),
                postingBase.getTextPadding(),
                titleHeight,
                postingBase.getTitleWidth() - postingBase.getTextPadding() * 2,
                postingBase.getContentMaxLine(),
                FontUtils.load_m(postingBase.getContentSize())
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
        if (postingBase.getBlogImageY() == -1) {
            return;
        }
        String imageUrl = posting.getBlogImage();

        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = BlogInfo.NOT_FOUND.getBlogThumb();
        }

        try {
            BufferedImage originalImage = ImageIO.read(new URL(imageUrl));
            BufferedImage circularImage = new BufferedImage(postingBase.getBlogImageWidth(), postingBase.getBlogImageHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = circularImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            g2d.fillOval(0, 0, postingBase.getBlogImageWidth(), postingBase.getBlogImageHeight());
            g2d.setComposite(AlphaComposite.SrcIn);
            g2d.drawImage(originalImage, 0, 0, postingBase.getBlogImageWidth(), postingBase.getBlogImageHeight(), null);
            g2d.dispose();

            svgGenerator.drawImage(circularImage, postingBase.getTextPadding(), postingBase.getBlogImageY(), null);

        } catch (IOException e) {
            log.error("Failed to load image from URL: {}", imageUrl, e);
        }
    }

    private void drawAuthorText(Posting posting, SVGGraphics2D svgGenerator, PostingBase postingBase) {
        if (postingBase.getBlogImageY() == -1) {
            return;
        }

        drawStroke(svgGenerator, 0, postingBase.getBoxWidth(),
                postingBase.getBlogImageY() - postingBase.getTextPadding() / 2,
                postingBase.getBlogImageY() - postingBase.getTextPadding() / 2 + 1);

        svgGenerator.setFont(FontUtils.load_m());

        String byText = "by ";
        drawText(svgGenerator, byText,
                postingBase.getTextPadding() + postingBase.getBlogImageWidth() * 3 / 2
                , postingBase.getBlogImageY() + postingBase.getBlogImageHeight() * 2 / 3,
                Color.GRAY);

        FontMetrics metrics = svgGenerator.getFontMetrics();
        drawText(svgGenerator, posting.getAuthor(),
                postingBase.getTextPadding() + postingBase.getBlogImageWidth() * 3 / 2 + metrics.stringWidth(byText)
                , postingBase.getBlogImageY() + postingBase.getBlogImageHeight() * 2 / 3,
                Color.BLACK);
    }

    private static void drawText(SVGGraphics2D svgGenerator, String text, int x, int y, Color color) {
        svgGenerator.setPaint(color);
        svgGenerator.drawString(text, x, y);
    }

    private synchronized void drawWatermark(Posting posting, SVGGraphics2D svgGenerator, PostingBase postingBase) {
        if (posting.getWatermark() == null || postingBase.getWatermarkX() == -1
                || postingBase.getWatermarkY() == -1) {
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
                    posting.getPostingBase().getWatermarkWidth(),
                    posting.getPostingBase().getWatermarkHeight());

            AffineTransform transform = new AffineTransform();
            transform.translate(postingBase.getWatermarkX(), postingBase.getWatermarkY());
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
                postingBase.getBoxWidth(), postingBase.getBoxHeight(),
                postingBase.getBoxArcWidth(), postingBase.getBoxArcHeight()
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
