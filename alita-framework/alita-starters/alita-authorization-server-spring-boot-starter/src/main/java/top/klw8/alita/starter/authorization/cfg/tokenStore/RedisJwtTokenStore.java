package top.klw8.alita.starter.authorization.cfg.tokenStore;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import top.klw8.alita.starter.authorization.utils.TokenUtil;
import top.klw8.alita.utils.redis.RedisUtil;
import top.klw8.alita.utils.redis.TokenRedisUtil;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: RedisJwtTokenStore
 * @Description: RedisJwtTokenStore
 * @date 2020/8/13 10:56
 */
public class RedisJwtTokenStore extends JwtTokenStore {

    public RedisJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer){
        super(jwtTokenEnhancer);
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 存储请求token,登录和刷新都会走这里,OAuth2AccessToken中包含刷新token
     * 刷新时不会走 storeRefreshToken ,但是OAuth2AccessToken中包含刷新token,所以刷新时生成的新的刷新token在这里放入redis
     * @Date 2020/8/14 17:32
     * @param: token
     * @param: authentication
     * @return void
     */
    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        // 登录认证通过生成token时和刷新token时会被调用
        String userId = TokenUtil.getUserId(token.getValue());
        TokenRedisUtil.storeAccessToken(userId, token.getValue());
        if(token.getRefreshToken() != null) {
            String refreshToken = token.getRefreshToken().getValue();
            if(StringUtils.isNotBlank(refreshToken)){
                TokenRedisUtil.storeRefreshToken(userId, refreshToken);
            }
        }
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 存储刷新token,只有登录时才会走这里,刷新不会走
     * @Date 2020/8/14 17:31
     * @param: refreshToken
     * @param: authentication
     * @return void
     */
    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        // 登录认证通过生成token时会被调用,刷新token时不会调用
        TokenRedisUtil.storeRefreshToken(TokenUtil.getUserId(refreshToken.getValue()), refreshToken.getValue());
    }

    /**
     * @author klw(213539@qq.com)
     * @Description: 这里验证redis中是否有该刷新token,有才允许刷新
     * @Date 2020/8/14 17:31
     * @param: tokenValue
     * @return org.springframework.security.oauth2.common.OAuth2RefreshToken
     */
    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        String cachedToken = TokenRedisUtil.getRefreshToken(TokenUtil.getUserId(tokenValue));
        if(StringUtils.isBlank(cachedToken)){
            throw new InvalidTokenException("刷新令牌不存在!");
        }
        if(!tokenValue.equals(cachedToken)){
            throw new InvalidTokenException("刷新令牌不正确!");
        }
        // 刷新token时会被调用,登录认证通过生成token时不会调用
        return super.readRefreshToken(tokenValue);
    }

}
