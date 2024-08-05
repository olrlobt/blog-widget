package olrlobt.githubtistoryposting.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostingType {

    BlogInfo(List.of("i", "info"), 217, 260, 217, 217, 0, 20, 217, 14, -1, 3),
    BlogPosting(List.of("p", "posting"), 217, 260, 217, 126, 0, 20, 217, 14, 126, 3),
    BlogPostingWide(List.of("w", "wide"), 800, 217, 217, 217, 583, 28, 583, 23, 10, 2),
    BlogPostingCard(List.of("c", "card"), 450, 130, 0, 0, 0, 25, 430, 14, 10, 1),
    BlogPostingBig(List.of("b", "big"), 320, 376, 320, 167, 0, 25, 288, 16, 183, 1),
    ;

    private final List<String> theme;
    private final int boxWidth;
    private final int boxHeight;
    private final int imgWidth;
    private final int imgHeight;
    private final int imgStartWidth;
    private final int textPadding;
    private final int titleWidth;
    private final int titleSize;
    private final int titleStartHeight;
    private final int titleMaxLine;

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
