package top.klw8.alita.demo.web.demo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import top.klw8.alita.demo.web.demo.parser.DemoResourceParser;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.starter.annotations.AuthorityRegister;
import top.klw8.alita.starter.datasecured.DataSecured;

import java.io.File;
import java.io.InputStream;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: DataAuthorityDemoController
 * @Description: 数据权限
 * @date 2020/4/22 13:57
 */
@Api(tags = {"alita-restful-API--demoAPI"})
@RestController
@RequestMapping("/${spring.application.name}/dataau")
public class DataAuthorityDemoController {

    @ApiOperation(value = "get测试", notes = "get测试", httpMethod = "GET", produces = "application/json")
    @GetMapping("/getTest")
    @AuthorityRegister(catlogName = "数据权限demo--get测试", catlogShowIndex = 99,
            authorityName = "get测试", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @DataSecured(resource = "getTest")
    public Mono<String> getTest(String str1, String str2){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>begin call 【get】");
        System.out.println("str1 ==== " + str1);
        System.out.println("str2 ==== " + str2);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<end call 【get】");
        return Mono.just("OK");
    }

    @ApiOperation(value = "post测试", notes = "post测试", httpMethod = "POST", produces = "application/json")
    @PostMapping("/postTest")
    @AuthorityRegister(catlogName = "数据权限demo--post测试", catlogShowIndex = 99,
            authorityName = "post测试", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @DataSecured(parser = DemoResourceParser.class)
    public Mono<String> postTest(String str1, @RequestBody String json){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>begin call 【post】");
        System.out.println("str1 ==== " + str1);
        System.out.println("json ==== " + json);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<end call 【post】");
        return Mono.just("OK");
    }

    @ApiOperation(value = "图片上传测试", notes = "图片上传测试", httpMethod = "POST", produces = "application/json")
    @PostMapping("/fileTest")
    @AuthorityRegister(catlogName = "数据权限demo--图片上传测试", catlogShowIndex = 99,
            authorityName = "图片上传测试", authorityType = AuthorityTypeEnum.URL,
            authorityShowIndex = 0)
    @DataSecured(resource = "fileTest", fileUpload = true)
    public Mono<String> fileTest(String str1, @RequestPart("str2") String str2, @RequestPart("file") FilePart filePart){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>begin call 【file】");
        System.out.println("str1 ==== " + str1);
        System.out.println("str2 ==== " + str2);
        System.out.println("fileName ====" + filePart.filename());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<end call 【file】");
        return DataBufferUtils.join(filePart.content()).map(buffer -> {
            if (buffer.readableByteCount() <= 0) {
                return "faild";
            }
            try {
                InputStream is = buffer.asInputStream(true);
                FileUtils.copyInputStreamToFile(is, new File("E:\\test\\testFileUpload.jpg"));
                is.close();
                DataBufferUtils.release(buffer);
            } catch (Exception e) {
                return e.getMessage();
            }
            return "OK";
        }).as(Mono::from);
    }

}
