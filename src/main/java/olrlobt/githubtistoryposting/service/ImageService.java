package olrlobt.githubtistoryposting.service;

import java.awt.*;
import java.awt.geom.Line2D;
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
    private final String TRUNCATE = "...";
    //    private final Color STROKE_COLOR = Color.decode("#d0d7de");
    private final Color STROKE_COLOR = new Color(139, 139, 139, 34);

    public byte[] createSvgImageBox(Posting posting) throws IOException {
        SVGGraphics2D svgGenerator = SvgUtils.init();
        PostingType postingType = posting.getPostingType();
        svgGenerator.setSVGCanvasSize(new java.awt.Dimension(postingType.getBoxWidth(), postingType.getBoxHeight()));
        drawBackground(svgGenerator, postingType);
        drawThumbnail(posting, svgGenerator, posting.getPostingType());
        drawText(posting, svgGenerator, posting.getPostingType());
        drawFooter(posting, svgGenerator, postingType);
        drawAuthor(posting, svgGenerator, postingType);
        drawStroke(svgGenerator, posting.getPostingType().getBoxWidth(), posting.getPostingType().getBoxHeight());

        return SvgUtils.toByte(svgGenerator);
    }

    private void drawBackground(SVGGraphics2D svgGenerator, PostingType postingType) {
        svgGenerator.setPaint(Color.WHITE);
        svgGenerator.fill(new Rectangle2D.Double(0, 0, postingType.getBoxWidth(), postingType.getBoxHeight()));
    }

    private void drawThumbnail(Posting posting, SVGGraphics2D svgGenerator, PostingType postingType) {
        String imageUrl = posting.getThumbnail();
        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = BlogInfo.NOT_FOUND.getBlogThumb();
        }

        try {
            BufferedImage originalImage = ImageIO.read(new URL(imageUrl));
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            int targetWidth = postingType.getImgWidth();
            int targetHeight = postingType.getImgHeight();
            int targetX = postingType.getImgStartWidth();
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

            Shape originalClip = svgGenerator.getClip();
            svgGenerator.setClip(targetX, targetY, targetWidth, targetHeight);
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

    private void drawText(Posting posting, SVGGraphics2D svgGenerator, PostingType postingType) {
        svgGenerator.setPaint(Color.BLACK);
        if (postingType.getTitleWeight() == 1) {
            svgGenerator.setFont(FontUtils.load_b(postingType.getTitleSize()));
        } else {
            svgGenerator.setFont(FontUtils.load_m(postingType.getTitleSize()));
        }

        if (postingType.getTitleStartHeight() >= 0) {
            int titleStart = (int) (postingType.getTitleStartHeight() + postingType.getTextPadding() * 1.5);
            int titleHeight = drawMultilineText(
                    svgGenerator,
                    posting.getTitle(),
                    postingType.getTextPadding(),
                    titleStart,
                    postingType.getTitleWidth() - postingType.getTextPadding() * 2,
                    postingType.getTitleMaxLine(),
                    svgGenerator.getFont()
            );

            if (postingType.getContentMaxLine() != -1 && !posting.getContent().isEmpty()) {
                drawMultilineText(
                        svgGenerator,
                        posting.getContent(),
                        postingType.getTextPadding(),
                        titleHeight,
                        postingType.getTitleWidth() - postingType.getTextPadding() * 2,
                        postingType.getContentMaxLine(),
                        FontUtils.load_m(postingType.getContentSize())
                );
            }
        }
    }

    private static void drawFooter(Posting posting, SVGGraphics2D svgGenerator, PostingType postingType) {
        if (postingType.getFooterType() == -1) {
            return;
        }

        svgGenerator.setFont(FontUtils.load_m());
        svgGenerator.setPaint(Color.GRAY);
        String footer = "";
        String publishedTime = posting.getPublishedTime();
        String url = posting.getUrl();
        int height = 0;
        if (postingType.getFooterType() == 1) {
            footer = (publishedTime != null && !publishedTime.isEmpty()) ? publishedTime : url;
            height = (publishedTime != null && !publishedTime.isEmpty()) ? postingType.getPublishedTimeStartHeight()
                    : postingType.getUrlStartHeight();
        } else if (postingType.getFooterType() == 2) {
            footer = (url != null && !url.isEmpty()) ? url : publishedTime;
            height = (url != null && !url.isEmpty()) ? postingType.getUrlStartHeight()
                    : postingType.getPublishedTimeStartHeight();
        } else if (postingType.getFooterType() == 0) {
            footer = publishedTime;
            svgGenerator.drawString(footer, postingType.getTextPadding(), postingType.getPublishedTimeStartHeight());
            svgGenerator.setFont(FontUtils.load_b());
            footer = posting.getSiteName();
            height = postingType.getUrlStartHeight();
        }
        svgGenerator.drawString(footer, postingType.getTextPadding(), height);
    }

    private void drawAuthor(Posting posting, SVGGraphics2D svgGenerator, PostingType postingType) {
        if (postingType.getBlogImageStartHeight() == -1) {
            return;
        }
        String imageUrl = posting.getBlogImage();

        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = BlogInfo.NOT_FOUND.getBlogThumb();
        }

        int width = 24;
        int height = 24;

        try {
            BufferedImage originalImage = ImageIO.read(new URL(imageUrl));

            // 원형 마스크 생성
            BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2dMask = mask.createGraphics();
            g2dMask.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2dMask.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2dMask.fillOval(0, 0, width, height);
            g2dMask.dispose();

            // 원형 마스크를 적용한 이미지 생성
            BufferedImage circularImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = circularImage.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.drawImage(originalImage, 0, 0, width, height, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
            g2d.drawImage(mask, 0, 0, null);
            g2d.dispose();

            // 원형 이미지를 svgGenerator에 그리기
            svgGenerator.drawImage(circularImage, postingType.getTextPadding(), postingType.getBlogImageStartHeight(),
                    null);
        } catch (IOException e) {
            log.error("Failed to load image from URL: {}", imageUrl, e);
        }

        drawStroke(svgGenerator, 0, postingType.getBoxWidth(),
                postingType.getBlogImageStartHeight() - postingType.getTextPadding() / 2,
                postingType.getBlogImageStartHeight() - postingType.getTextPadding() / 2 + 1);

        svgGenerator.setFont(FontUtils.load_m());
        String byText = "by ";
        svgGenerator.setPaint(Color.GRAY);
        svgGenerator.drawString(byText, postingType.getTextPadding() + width * 3 / 2,
                postingType.getBlogImageStartHeight() + height * 2 / 3);

// 글자 폭 계산 (다음 텍스트의 시작 위치를 정하기 위해)
        FontMetrics metrics = svgGenerator.getFontMetrics();
        int byTextWidth = metrics.stringWidth(byText);

// author 부분
        String authorText = posting.getAuthor();
        svgGenerator.setPaint(Color.BLACK);
        svgGenerator.drawString(authorText, postingType.getTextPadding() + width * 3 / 2 + byTextWidth,
                postingType.getBlogImageStartHeight() + height * 2 / 3);
    }


    private void drawStroke(SVGGraphics2D svgGenerator, int BOX_WIDTH, int TOTAL_HEIGHT) {
        svgGenerator.setPaint(STROKE_COLOR);
        svgGenerator.draw(new Rectangle2D.Double(0, 0, BOX_WIDTH - 1, TOTAL_HEIGHT - 1));
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
                    currentLine = new StringBuilder();
                } else {
                    if (lineWidth > maxWidth) {
                        while (metrics.stringWidth(lineText + TRUNCATE) > maxWidth && lineText.length() > 0) {
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

        if (currentLine.length() > 0 && linesCount < maxLines) {
            svgGenerator.drawString(currentLine.toString(), startX, startY + linesCount * lineHeight);
            linesCount++;
        }
        return startY + linesCount * lineHeight;
    }
}
