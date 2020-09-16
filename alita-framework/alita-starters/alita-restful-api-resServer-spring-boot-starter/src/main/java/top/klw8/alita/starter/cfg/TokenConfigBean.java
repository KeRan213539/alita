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
    
    private List<String> defaultCheckExcludePaths = Arrays
            .asList(new String[] {"/devHelper/**", "/swagger-ui.html**", "/webjars/**", "/swagger-ui/**",
                    "/swagger-resources/**", "/static/**", "/js/**"});
    
    private List<String> mergedCheckExcludePaths;
    
    public List<String> getCheckExcludePaths(){
        return this.mergedCheckExcludePaths;
    }
    
    public void setCheckExcludePaths(List<String> list){
        if(CollectionUtils.isEmpty(list)) {
            mergedCheckExcludePaths = defaultCheckExcludePaths;
        } else {
            mergedCheckExcludePaths = new ArrayList<>(defaultCheckExcludePaths.size() + list.size());
            mergedCheckExcludePaths.addAll(defaultCheckExcludePaths);
            mergedCheckExcludePaths.addAll(list);
        }
    }
    
}
