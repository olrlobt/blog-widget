package olrlobt.githubtistoryposting.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.util.XMLResourceDescriptor;
import org.springframework.core.io.Resource;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.svg.SVGDocument;

public class SvgUtils {

	private static final String NAMESPACE_URL = "http://www.w3.org/2000/svg";
	private static final String SVG_ELEMENT = "svg";

	public static SVGGraphics2D init(){
		DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
		Document document = domImpl.createDocument(NAMESPACE_URL, SVG_ELEMENT, null);
		return new SVGGraphics2D(document);
	}

	public static byte[] toByte(SVGGraphics2D svgGenerator) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Writer out = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
		svgGenerator.stream(out, true);
		out.close();
		return outputStream.toByteArray();
	}

	public static SVGDocument loadSVGDocument(Resource resource) {
		try (InputStream inputStream = resource.getInputStream()) {
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
			return factory.createSVGDocument(null, inputStream);
		} catch (Exception e) {
			return null;
		}
	}
}
