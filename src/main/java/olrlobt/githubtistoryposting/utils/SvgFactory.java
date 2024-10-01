package olrlobt.githubtistoryposting.utils;

import java.awt.RenderingHints;
import olrlobt.githubtistoryposting.domain.PostingBase;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.stereotype.Component;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.apache.commons.pool2.BasePooledObjectFactory;

@Component
public class SvgFactory extends BasePooledObjectFactory<SVGGraphics2D> {
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
        // 객체 반환 시 초기화 작업 (예: 객체 상태 초기화)
        SVGGraphics2D svgGenerator = pooledObject.getObject();
        svgGenerator.setSVGCanvasSize(new java.awt.Dimension(0, 0));
    }
}
