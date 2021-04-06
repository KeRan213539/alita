package top.klw8.alita.starter.web.base;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.synchronoss.cloud.nio.multipart.MultipartUtils;
import reactor.core.publisher.Flux;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SynchronossFormFieldPart
 * @Description: copy from org.springframework.http.codec.multipart.SynchronossPartHttpMessageReader$SynchronossFormFieldPart
 * @date 2020/4/23 17:35
 */
public class SynchronossFormFieldPart extends AbstractSynchronossPart implements FormFieldPart {

    private final String content;

    public SynchronossFormFieldPart(HttpHeaders headers, DataBufferFactory bufferFactory, String content) {
        super(headers, bufferFactory);
        this.content = content;
    }

    @Override
    public String value() {
        return this.content;
    }

    @Override
    public Flux<DataBuffer> content() {
        byte[] bytes = this.content.getBytes(getCharset());
        DataBuffer buffer = getBufferFactory().allocateBuffer(bytes.length);
        buffer.write(bytes);
        return Flux.just(buffer);
    }

    private Charset getCharset() {
        String name = MultipartUtils.getCharEncoding(headers());
        return (name != null ? Charset.forName(name) : StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return "Part '" + name() + "=" + this.content + "'";
    }

}
