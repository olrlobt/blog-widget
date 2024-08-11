package olrlobt.githubtistoryposting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Dimensions {
    private int width;
    private int height;
    private int x;
    private int y;

    public Dimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Dimensions EMPTY = new Dimensions(-1, -1, -1, -1);
}
