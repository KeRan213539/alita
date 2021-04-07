package top.klw8.alita.starter.authorization.endpoints;

/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    @PostMapping(value = "/oauth/logout", produces = "text/html;charset=utf-8")
    public ResponseEntity<String> logout(String refreshToken){
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
