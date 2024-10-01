package olrlobt.githubtistoryposting.utils;

import jakarta.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.svg.SVGDocument;

@Component
@Lazy(false)
public class SvgPool {
    private final GenericObjectPool<SVGGraphics2D> pool;

    public SvgPool() {
        GenericObjectPoolConfig<SVGGraphics2D> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(20); // 최대 풀 크기 설정
        config.setMinIdle(10);   // 최소 유휴 객체 수
        config.setMaxIdle(20);   // 최대 유휴 객체 수
        config.setBlockWhenExhausted(true); // 풀에 여유 객체가 없을 때 대기

        this.pool = new GenericObjectPool<>(new SvgFactory(), config);
    }

    // 객체 대여
    public SVGGraphics2D borrowObject() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void initializePool() {
        try {
            for (int i = 0; i < pool.getMinIdle(); i++) {
                pool.addObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 객체 반환
    public void returnObject(SVGGraphics2D svgGraphics2D) {
        pool.returnObject(svgGraphics2D);
    }

    public byte[] toByte(SVGGraphics2D svgGenerator) throws IOException {
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
