package olrlobt.githubtistoryposting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.svg.SVGDocument;

@Component
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Watermark {
    private SVGDocument svgDocument;
    private String color;
}
