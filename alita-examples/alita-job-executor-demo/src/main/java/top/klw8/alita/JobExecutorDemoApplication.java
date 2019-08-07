package top.klw8.alita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LoggingSystem;

/**
 * @ClassName: JobExecutorApplication
 * @Description: 定时任务执行器 demo
 * @author klw
 * @date 2018年10月23日 下午3:08:05
 */
@SpringBootApplication
public class JobExecutorDemoApplication {

    public static void main(String[] args) {
	System.setProperty("org.springframework.boot.logging.LoggingSystem", LoggingSystem.NONE);  // 彻底关闭 spring boot 自带的 LoggingSystem
	SpringApplication.run(JobExecutorDemoApplication.class, args);
    }
    

}