package olrlobt.githubtistoryposting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.w3c.dom.svg.SVGDocument;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Watermark {
    private SVGDocument svgDocument;
    private String color;

    public Watermark clone() {
        return new Watermark((SVGDocument) svgDocument.cloneNode(true), this.color);
    }
}
