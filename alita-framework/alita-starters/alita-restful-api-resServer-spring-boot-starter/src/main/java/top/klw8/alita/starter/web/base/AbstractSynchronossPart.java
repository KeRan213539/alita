/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.starter.web.base;

import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.Assert;
import org.synchronoss.cloud.nio.multipart.MultipartUtils;

/**
 * copy from org.springframework.http.codec.multipart.SynchronossPartHttpMessageReader$AbstractSynchronossPart
 * 2020/4/23 17:37
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
