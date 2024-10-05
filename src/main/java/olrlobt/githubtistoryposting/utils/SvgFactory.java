package olrlobt.githubtistoryposting.utils;

import java.awt.Dimension;
import java.awt.RenderingHints;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

@Component
public class SvgFactory extends BasePooledObjectFactory<SVGGraphics2D> {

    private final Dimension DEFAULT_CANVAS_SIZE = new Dimension(0, 0);

    @Override
    public SVGGraphics2D create() {
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        svgGenerator.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        svgGenerator.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        svgGenerator.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        svgGenerator.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        svgGenerator.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        svgGenerator.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        return svgGenerator;
    }

    @Override
    public PooledObject<SVGGraphics2D> wrap(SVGGraphics2D svgGraphics2D) {
        return new DefaultPooledObject<>(svgGraphics2D);
    }

    @Override
    public void passivateObject(PooledObject<SVGGraphics2D> pooledObject) {
        SVGGraphics2D svgGenerator = pooledObject.getObject();
        svgGenerator.setSVGCanvasSize(DEFAULT_CANVAS_SIZE);
    }
}
