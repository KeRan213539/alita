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
import top.klw8.alita.fileupload.helpers.FileUploadTypeEnum;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.starter.web.common.CallBackMessage;
import top.klw8.alita.starter.web.common.JsonResult;
import top.klw8.alita.starter.web.common.enums.ResultCodeEnum;
import top.klw8.alita.starter.web.common.enums.ResultStatusEnum;
import top.klw8.alita.utils.DateTimeUtil;

/**
 * @ClassName: AliyunOSSController
 * @Description: 文件上传
 * @author klw
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
	    return Mono.just(JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._400, "文件名不能为空", null, false));
	}

	String fileName = filePart.filename();
	String suffix = fileName.substring(fileName.lastIndexOf("."));
	String ossFileName = UUID.randomUUID().toString() + suffix; // 使用uuid作为文件名
	String ossObjKey = FileUploadTypeEnum.IMG_FILE_PATH.getTypePath() + "/"
		+ DateTimeUtil.getTodayChar8() + "/" + ossFileName;
	
	return DataBufferUtils.join(filePart.content()).map(buffer -> {
	    if (buffer.readableByteCount() <= 0) {
		return JsonResult.sendResult(ResultStatusEnum.FAILED, ResultCodeEnum._400,
			CallBackMessage.fileEmpty, null, false);
	    }
	    try {
		InputStream is = buffer.asInputStream();
		ossClient.putObject(FileUploadTypeEnum.OSS_BUCKET_NAME.getTypePath(), ossObjKey, is);
		is.close();
		is = null;
		DataBufferUtils.release(buffer);
	    } catch (Exception e) {
		e.printStackTrace();
		return JsonResult.sendFailedResult("文件上传失败！", null);
	    }
	    Map<String, String> resultMap = new HashMap<String, String>();
	    resultMap.put("filePath", ossObjKey);
	    resultMap.put("fileName", ossFileName);
	    return JsonResult.sendResult(ResultStatusEnum.SUCCESS, ResultCodeEnum._200, CallBackMessage.saveSuccess, resultMap, true);
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
	    return Mono.just(JsonResult.sendParamError());
	}

	try {
	    ossClient.deleteObject(FileUploadTypeEnum.OSS_BUCKET_NAME.getTypePath(), filePath);
	} catch (OSSException | ClientException e) {
	    e.printStackTrace();
	    return Mono.just(JsonResult.sendFailedResult("删除OSS文件失败", e.getMessage()));
	}
	return Mono.just(JsonResult.sendResult(ResultStatusEnum.SUCCESS, ResultCodeEnum._200, CallBackMessage.deleteSuccess, null, true));

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
