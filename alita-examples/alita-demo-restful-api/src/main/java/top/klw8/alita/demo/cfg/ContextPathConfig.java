package top.klw8.alita.demo.cfg;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName ContextPathConfig
 * @Description TODO 下面被注释的部分主要提供springboot使用将配置在程序启动的时候就最先加载配置以便controller的@RequestMapping("${..}")的方式可以注入配置并解析，在spring cloud alibaba中已经替我们做过同样的事情故在apring cloud alibaba中不需要使用
 * @Author zhanglei
 * @Date 2019/8/27 17:49
 * @Version 1.0
 */
//@Configuration
//public class ContextPathConfig implements WebMvcConfigurer  {
//
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        System.out.println("==================== init PropertySourcesPlaceholderConfigurer ==========================");
//        return new PropertySourcesPlaceholderConfigurer();
//    }
//
//}
