package top.klw8.alita.authorization.cfg;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.starter.common.UserCacheHelper;

/**
 * @author klw
 * @ClassName: CustomTokenEnhancer
 * @Description: token中的附加数据
 * @date 2018年11月2日 下午4:43:03
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    private IAlitaUserProvider userService;

    private UserCacheHelper userCacheHelper;

    public CustomTokenEnhancer(IAlitaUserProvider userService, UserCacheHelper userCacheHelper) {
        this.userService = userService;
        this.userCacheHelper = userCacheHelper;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 这里可以做一些其他事情,比如从库里查一些数据放到token里面,或者把一些东西放redis
        // token 里不能放敏感信息,因为token只是base64编码,可以反编码出来.虽然要加上HTTPS,但是也不是绝对安全
        // authentication.getName() 可以拿到登录的用户名
        // authentication 里面包含用户实体  authentication.getUserAuthentication().getPrincipal()

        //token 中也不要放太多内容了,特别是敏感信息. 还是放在缓存里吧
        Map<String, Object> additionalInfo = new HashMap<>();
        Object principal = authentication.getUserAuthentication().getPrincipal();
        AlitaUserAccount user = null;
        if (principal instanceof AlitaUserAccount) {
            user = (AlitaUserAccount) principal;
            additionalInfo.put("userId", user.getId().toString());
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        //重新查询user,把相关信息放入缓存
		CompletableFuture<AlitaUserAccount> accountFuture = userService.findUserById(user.getId());
        try {
            userCacheHelper.putUserInfo2Cache(accountFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

}
