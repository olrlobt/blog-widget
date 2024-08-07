package olrlobt.githubtistoryposting.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostingType {

    BlogInfo(List.of("i", "info"), 217, 260, 217, 217, 0, 20, -1, 217, 14, -1, 3, -1, -1, -1, 2, 240, 240, -1),
    BlogPosting(List.of("p", "posting"), 217, 260, 217, 126, 0, 20, -1, 217, 14, 126, 3, -1, -1, -1, 1, 240, 240, -1),
    BlogPostingWide(List.of("w", "wide"), 800, 217, 217, 217, 583, 28, -1, 583, 23, 10, 2, 1, 3, 14, 1, 189, 189, -1),
    BlogPostingCard(List.of("c", "card"), 450, 130, 0, 0, 0, 25, 1, 430, 14, 10, 1, 1, 1, 12, 0, 105, 30, -1),
    BlogPostingBig(List.of("b", "big"), 320, 376, 320, 167, 0, 20, 1, 300, 16, 180, 1, 1, 3, 14, 1, 320, 320, 340),
    ;

    private final List<String> theme;
    private final int boxWidth;
    private final int boxHeight;
    private final int imgWidth;
    private final int imgHeight;
    private final int imgStartWidth;
    private final int textPadding;
    private final int titleWeight;
    private final int titleWidth;
    private final int titleSize;
    private final int titleStartHeight;
    private final int titleMaxLine;
    private final int contentHeight;
    private final int contentMaxLine;
    private final int contentSize;
    private final int footerType; // empty : -1, both : 0 ,publishedTime : 1 , url : 2
    private final int publishedTimeStartHeight;
    private final int urlStartHeight;
    private final int blogImageStartHeight;

    public static PostingType getTheme(String key) {
        if (key == null || key.isEmpty()) {
            return BlogPosting;
        }

        for (PostingType type : PostingType.values()) {
            if (type.theme.contains(key)) {
                return type;
            }
        }
        return BlogPosting;
    }
}
