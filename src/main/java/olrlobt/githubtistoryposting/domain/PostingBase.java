package olrlobt.githubtistoryposting.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostingBase {

    BlogInfo(List.of("i", "info"), 217, 260, 10, 10, 217, 217, 0, 20, -1, -1, -1, -1, -1, -1, -1, -1, 2, 240, 240, -1,
            -1, -1, -1, -1, -1, -1),
    BlogPosting(List.of("p", "posting"), 217, 260, 10, 10, 217, 126, 0, 20, -1, 217, 14, 155, 3, -1, -1, -1, 1, 240,
            240, -1, -1, -1, -1, -1, -1, -1),
    BlogPostingWide(List.of("w", "wide"), 800, 217, 10, 10, 217, 217, 583, 28, -1, 583, 23, 50, 2, 1, 3, 14, 1, 189,
            189, -1, -1, -1, -1, -1, -1, -1),
    BlogPostingCard(List.of("c", "card"), 450, 130, 10, 10, 0, 0, 0, 25, 1, 430, 14, 62, 1, 1, 1, 12, 0, 105, 35, -1,
            -1, -1, 420, 27, 10, 10),
    BlogPostingBig(List.of("b", "big"), 320, 376, 10, 10, 320, 167, 0, 20, 1, 300, 16, 200, 1, 1, 3, 14, 1, 320, 320,
            340, 24, 24, -1, -1, -1, -1),
    ;

    private final List<String> theme;
    private final int boxWidth;
    private final int boxHeight;
    private final int boxArcWidth;
    private final int boxArcHeight;
    private final int imgWidth;
    private final int imgHeight;
    private final int imgX;
    private final int textPadding;
    private final int titleWeight;
    private final int titleWidth;
    private final int titleSize;
    private final int titleY;
    private final int titleMaxLine;
    private final int contentHeight;
    private final int contentMaxLine;
    private final int contentSize;
    private final int footerType; // empty : -1, both : 0 ,publishedTime : 1 , url : 2
    private final int publishedTimeY;
    private final int urlY;
    private final int blogImageY;
    private final int blogImageWidth;
    private final int blogImageHeight;
    private final int watermarkX;
    private final int watermarkY;
    private final int watermarkWidth;
    private final int watermarkHeight;

    public static PostingBase getTheme(String key) {
        if (key == null || key.isEmpty()) {
            return BlogPosting;
        }

        for (PostingBase type : PostingBase.values()) {
            if (type.theme.contains(key)) {
                return type;
            }
        }
        return BlogPosting;
    }
}
