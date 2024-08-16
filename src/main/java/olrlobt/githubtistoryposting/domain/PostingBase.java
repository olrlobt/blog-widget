package olrlobt.githubtistoryposting.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostingBase {

    BlogInfo(List.of("i", "info"),
            new Dimensions(217, 260),
            new Dimensions(10, 10),
            new Dimensions(217, 217, 0, -1),
            TextDimensions.EMPTY,
            TextDimensions.EMPTY,
            Dimensions.EMPTY,
            Dimensions.EMPTY,
            20, 2, 240, 240),
    BlogPosting(List.of("p", "posting"),
            new Dimensions(217, 260),
            new Dimensions(10, 10),
            new Dimensions(217, 126, 0, -1),
            new TextDimensions(217, -1, -1, 155, 14, 3, -1),
            TextDimensions.EMPTY,
            Dimensions.EMPTY,
            Dimensions.EMPTY,
            20, 1, 240, 240),
    BlogPostingWide(List.of("w", "wide"),
            new Dimensions(800, 217),
            new Dimensions(10, 10),
            new Dimensions(217, 217, 583, -1),
            new TextDimensions(583, -1, -1, 50, 23, 2, -1),
            new TextDimensions(-1, 1, 14, 3, -1),
            Dimensions.EMPTY,
            Dimensions.EMPTY,
            28, 1, 189, 189),
    BlogPostingCard(List.of("c", "card"),
            new Dimensions(450, 130),
            new Dimensions(10, 10),
            Dimensions.EMPTY,
            new TextDimensions(430, -1, -1, 62, 14, 1, 1),
            new TextDimensions(-1, 1, 12, 1, -1),
            Dimensions.EMPTY,
            new Dimensions(12, 12, 420, 24),
            25, 0, 105, 35),
    BlogPostingBig(List.of("b", "big"),
            new Dimensions(320, 376),
            new Dimensions(10, 10),
            new Dimensions(320, 167, 0, -1),
            new TextDimensions(300, -1, -1, 200, 16, 1, 1),
            new TextDimensions(-1, 1, 14, 3, -1),
            new Dimensions(24, 24, -1, 340),
            Dimensions.EMPTY,
            20, 1, 320, 320),
    ;

    private final List<String> theme;
    private final Dimensions box;
    private final Dimensions boxArc;
    private final Dimensions img;
    private final TextDimensions title;
    private final TextDimensions content;
    private final Dimensions blogImage;
    private final Dimensions watermark;
    private final int textPadding;
    private final int footerType; // empty : -1, both : 0 ,publishedTime : 1 , url : 2
    private final int publishedTimeY;
    private final int urlY;

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
