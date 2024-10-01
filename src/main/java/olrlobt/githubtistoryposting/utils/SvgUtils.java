package olrlobt.githubtistoryposting.utils;

import java.io.InputStream;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.springframework.core.io.Resource;
import org.w3c.dom.svg.SVGDocument;

public class SvgUtils {

    private static final String PARSER = XMLResourceDescriptor.getXMLParserClassName();
    private static final SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(PARSER);

    public static SVGDocument loadSVGDocument(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            return factory.createSVGDocument(null, inputStream);
        } catch (Exception e) {
            return null;
        }
    }
}
