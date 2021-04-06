package top.klw8.alita.starter.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;


/**
 * @author klw
 * @ClassName: WebFluxConfiguration
 * @Description: web 配制
 * @date 2018年12月6日 下午2:49:39
 */
@Configuration
@EnableWebFlux
@ComponentScan("top.klw8.alita.common.web")
public class WebFluxConfiguration implements WebFluxConfigurer {

    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");

        String[] activeprofiles = env.getActiveProfiles();
        for (String activeprofile : activeprofiles) {
            if (activeprofile.equals("dev")) {
                // dev 模式下设置 swagger
                registry.addResourceHandler("/swagger-ui.html**")
                        .addResourceLocations("classpath:/static/").addResourceLocations("classpath:/META-INF/resources/");
                registry.addResourceHandler("/webjars/**")
                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
                break;
            }
        }

    }

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.freeMarker();
	}

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("classpath:/ftls");
		return configurer;
	}

//    public void addInterceptors(InterceptorRegistry registry) {
//	List<String> excludePathPatterns = new ArrayList<>();
//	excludePathPatterns.add("/static/**");
//	excludePathPatterns.add("/js/**");
//	excludePathPatterns.add("/error");
//
//	String[] activeprofiles = env.getActiveProfiles();
//	for (String activeprofile : activeprofiles) {
//	    if (activeprofile.equals("dev")) {
//		// dev 模式下设置 swagger
//		excludePathPatterns.add("/swagger-ui.html");
//		excludePathPatterns.add("/webjars/**");
//		excludePathPatterns.add("/swagger-resources/**");
//		excludePathPatterns.add("/devHelper/**");
//		break;
//	    }
//	}
//	registry.addInterceptor(authorityInterceptor).addPathPatterns("/**")
//		.excludePathPatterns(excludePathPatterns);
//    }

}
