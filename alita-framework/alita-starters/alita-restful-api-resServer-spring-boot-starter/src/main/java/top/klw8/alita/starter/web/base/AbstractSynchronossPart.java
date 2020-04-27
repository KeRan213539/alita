package top.klw8.alita.starter.web.base;

import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.Assert;
import org.synchronoss.cloud.nio.multipart.MultipartUtils;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AbstractSynchronossPart
 * @Description: copy from org.springframework.http.codec.multipart.SynchronossPartHttpMessageReader$AbstractSynchronossPart
 * @date 2020/4/23 17:37
 */
public abstract class AbstractSynchronossPart implements Part {

    private final String name;

    private final HttpHeaders headers;

    private final DataBufferFactory bufferFactory;

    AbstractSynchronossPart(HttpHeaders headers, DataBufferFactory bufferFactory) {
        Assert.notNull(headers, "HttpHeaders is required");
        Assert.notNull(bufferFactory, "DataBufferFactory is required");
        this.name = MultipartUtils.getFieldName(headers);
        this.headers = headers;
        this.bufferFactory = bufferFactory;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public HttpHeaders headers() {
        return this.headers;
    }

    DataBufferFactory getBufferFactory() {
        return this.bufferFactory;
    }

    @Override
    public String toString() {
        return "Part '" + this.name + "', headers=" + this.headers;
    }

}
