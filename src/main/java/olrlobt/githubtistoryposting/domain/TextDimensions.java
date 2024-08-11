package olrlobt.githubtistoryposting.domain;

import lombok.Getter;

@Getter
public class TextDimensions extends Dimensions {
    private final int size;
    private final int maxLine;
    private final int weight;

    public TextDimensions(int width, int height, int size, int maxLine, int weight) {
        super(width, height);
        this.size = size;
        this.maxLine = maxLine;
        this.weight = weight;
    }

    public TextDimensions(int width, int height, int x, int y, int size, int maxLine, int weight) {
        super(width, height, x, y);
        this.size = size;
        this.maxLine = maxLine;
        this.weight = weight;
    }

    public static TextDimensions EMPTY = new TextDimensions(-1, -1, -1, -1, -1, -1, -1);
}