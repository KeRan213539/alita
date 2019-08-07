package top.klw8.alita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Bean;

import com.aliyun.oss.OSS;

import top.klw8.alita.fileupload.helpers.FileUploadTypeEnum;
import top.klw8.alita.starter.annotations.BaseWebApiApplication;


/**
 * @ClassName: FileUploadWebApiApplication
 * @Description: 文件上传 接口服务启动器
 * @author klw
 * @date 2018-11-19 15:57:49
 */
public class FileUploadWebApiApplication extends BaseWebApiApplication {

    public static void main(String[] args) {
	System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
        SpringApplication.run( FileUploadWebApiApplication.class, args );
    }
    
    @Bean
    public AppRunner appRunner() {
        return new AppRunner();
    }

    class AppRunner implements ApplicationRunner {
        @Autowired
        private OSS ossClient;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            try {
                if (!ossClient.doesBucketExist(FileUploadTypeEnum.OSS_BUCKET_NAME.getTypePath())) {
                    ossClient.createBucket(FileUploadTypeEnum.OSS_BUCKET_NAME.getTypePath());
                }
            } catch (Exception e) {
                System.err.println("oss handle bucket error: " + e.getMessage());
                System.exit(-1);
            }
        }
    }
    
}