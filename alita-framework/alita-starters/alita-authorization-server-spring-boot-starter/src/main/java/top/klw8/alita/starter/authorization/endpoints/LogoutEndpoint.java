package top.klw8.alita.starter.authorization.endpoints;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登出.
 *
 * @author klw(213539 @ qq.com)
 * @ClassName: LogoutEndpoint
 * @date 2020/9/10 18:11
 */
@RestController
public class LogoutEndpoint {
    
    @Autowired
    private TokenStore tokenStore;

    @PostMapping("/oauth/logout")
    public ResponseEntity logout(String refreshToken){
        if(StringUtils.isBlank(refreshToken)){
            return new ResponseEntity("刷新令牌不能为空", HttpStatus.BAD_REQUEST);
        }
        try {
            tokenStore.removeAccessTokenUsingRefreshToken(tokenStore.readRefreshToken(refreshToken));
            return new ResponseEntity("OK", HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity("刷新令牌不正确", HttpStatus.BAD_REQUEST);
        }
        
    }
    
    
}
