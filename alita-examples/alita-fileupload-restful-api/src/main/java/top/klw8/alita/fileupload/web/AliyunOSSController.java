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
package top.klw8.alita.fileupload.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.fileupload.cfg.resultCode.ResultSubCodeEnum;
import top.klw8.alita.fileupload.helpers.FileUploadTypeEnum;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.utils.DateTimeUtil;

/**
 * @author klw
 * @ClassName: AliyunOSSController
 * @Description: 文件上传
 * @date 2018年11月19日 下午12:02:25
 */
@RestController
@RequestMapping("/oss")
public class AliyunOSSController extends WebapiBaseController {

    @Autowired
    private OSS ossClient;

    @ApiOperation(value = "上传文件到阿里云OSS", notes = "上传单个文件到阿里云OSS", httpMethod = "POST", produces = "application/json")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @AuthorityRegister(catlogName = "文件上传相关", catlogShowIndex = 98,
            authorityName = "上传文件到阿里云OSS", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    public Mono<JsonResult> uploadToOSS(@RequestPart("file") FilePart filePart) throws Exception {
        if (StringUtils.isBlank(filePart.filename())) {
            return Mono.just(JsonResult.failed(ResultSubCodeEnum.FILE_NAME_EMPTY));
        }

        String fileName = filePart.filename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String ossFileName = UUID.randomUUID().toString() + suffix; // 使用uuid作为文件名
        String ossObjKey = FileUploadTypeEnum.IMG_FILE_PATH.getTypePath() + "/"
                + DateTimeUtil.getTodayChar8() + "/" + ossFileName;

        return DataBufferUtils.join(filePart.content()).map(buffer -> {
            if (buffer.readableByteCount() <= 0) {
                return JsonResult.failed(ResultSubCodeEnum.FILE_DATA_EMPTY);
            }
            try {
                InputStream is = buffer.asInputStream();
                ossClient.putObject(FileUploadTypeEnum.OSS_BUCKET_NAME.getTypePath(), ossObjKey, is);
                is.close();
                is = null;
                DataBufferUtils.release(buffer);
            } catch (Exception e) {
                return JsonResult.failed(ResultSubCodeEnum.FILE_UPLOAD_FAIL);
            }
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("filePath", ossObjKey);
            resultMap.put("fileName", ossFileName);
            return JsonResult.successfu(resultMap);
        }).as(Mono::from);
    }

    /**
     * 从OSS删除文件
     *
     * @param
     * @throws IOException
     */
    @ApiOperation(value = "删除阿里云OSS中的文件", notes = "删除阿里云OSS中的文件", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "filePath", value = "OSS中的文件路径(上传时返回的)", required = true)
    })
    @PostMapping(value = "/delete")
    @AuthorityRegister(catlogName = "文件上传相关", catlogShowIndex = 98,
            authorityName = "删除阿里云OSS中的文件", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    public Mono<JsonResult> deleteFromOSS(@RequestParam String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return Mono.just(JsonResult.failed(ResultSubCodeEnum.DELETE_OSS_FILE_FAIL));
        }

        try {
            ossClient.deleteObject(FileUploadTypeEnum.OSS_BUCKET_NAME.getTypePath(), filePath);
        } catch (OSSException | ClientException e) {
            return Mono.just(JsonResult.failed(ResultSubCodeEnum.DELETE_OSS_FILE_FAIL, e.getMessage()));
        }
        return Mono.just(JsonResult.successfu());

    }

    //文件下载例子
//    @GetMapping("/download")
//    public Mono<Void> downloadByWriteWith(ServerHttpResponse response) throws IOException {
//        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
//        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=parallel.png");
//        response.getHeaders().setContentType(MediaType.IMAGE_PNG);
//
//        Resource resource = new ClassPathResource("parallel.png");
//        File file = resource.getFile();
//        return zeroCopyResponse.writeWith(file, 0, file.length());
//    }

}
