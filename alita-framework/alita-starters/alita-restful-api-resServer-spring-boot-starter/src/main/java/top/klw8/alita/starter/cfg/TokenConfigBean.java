package top.klw8.alita.starter.cfg;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * alita.oauth2.token 配制.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: TokenConfigBean
 * @date 2020/9/11 11:10
 */
@ConfigurationProperties(prefix="alita.oauth2.token")
public class TokenConfigBean {
    
    @Getter
    @Setter
    private boolean storeInRedis;
    
    @Setter
    private List<String> checkExcludePaths;
    
    private List<String> defaultCheckExcludePaths = Arrays
            .asList(new String[] {"/devHelper/**", "/swagger-ui.html**", "/webjars/**", "/swagger-ui/**",
                    "/swagger-resources/**", "/static/**", "/js/**"});
    
    private List<String> mergedCheckExcludePaths;
    
    public List<String> getCheckExcludePaths(){
        if(mergedCheckExcludePaths == null){
            if(CollectionUtils.isEmpty(checkExcludePaths)) {
                mergedCheckExcludePaths = defaultCheckExcludePaths;
            } else {
                mergedCheckExcludePaths = new ArrayList<>(defaultCheckExcludePaths.size() + checkExcludePaths.size());
                mergedCheckExcludePaths.addAll(defaultCheckExcludePaths);
                mergedCheckExcludePaths.addAll(checkExcludePaths);
            }
        }
        return this.mergedCheckExcludePaths;
    }
    
}
