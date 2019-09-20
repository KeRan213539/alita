package top.klw8.alita;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


/**
 * @author klw
 * @ClassName: DubboDebugToolServerApplication
 * @Description: 启动 dubbo调试工具
 * @date 2019-09-19 17:23:17
 */
@SpringBootApplication
@RestController
public class DubboDebugToolServerApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DubboDebugToolServerApplication.class);

        ApplicationConfig application = new ApplicationConfig();
        application.setName("alita-dubbo-debug-tool");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("nacos://127.0.0.1:8848");
        registryConfig.setRegister(false);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>(); // 该类很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registryConfig); // 多个注册中心可以用setRegistries()

        reference.setInterface("com.yd.alita.provider.test.ITestProvider");

        // 声明为泛化接口
        reference.setGeneric(true);


        GenericService genericService = reference.get();

        Map<String, Object> catlog = new HashMap<>();
        catlog.put("catlogName", "test4");
        catlog.put("showIndex", 1);
        catlog.put("remark", "测试");

        // 基本类型以及Date,List,Map等不需要转换，直接调用
//        Object result = genericService.$invoke("findUserById", new String[] {"java.lang.String"}, new Object[] {"111"});
        CompletableFuture future = genericService.$invokeAsync("addCatlog", new String[] {"top.klw8.alita.entitys.authority.SystemAuthoritysCatlog"}, new Object[] {catlog});
//        CompletableFuture future = genericService.$invokeAsync("test", null, null);
        future.whenComplete((r, ex) -> {
            if(null != ex){
                ((Exception)ex).printStackTrace();
            } else {
                System.out.println(r);
            }
        });

    }

}
