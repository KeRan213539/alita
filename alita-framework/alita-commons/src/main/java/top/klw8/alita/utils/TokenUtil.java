package top.klw8.alita.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Map;

/**
 * @author klw
 * @ClassName: TokenUtil
 * @Description: token 工具
 * @date 2018年11月30日 上午11:58:27
 */
@Slf4j
public class TokenUtil {
    
    public static final String APP_CHANNEL_COMBINED_CLIENT_SPLIT = "@@";

    /**
     * @return
     * @Title: getUserId
     * @author klw
     * @Description: 获取token中的userId
     */
    public static String getUserId(String jwtToken) {
        return (String) getTokenAdditionalData(jwtToken, "userId");
    }
    
    public static String[] getAppTagAndChannelTag(String jwtToken){
        return ((String) getTokenAdditionalData(jwtToken, "client_id")).split(APP_CHANNEL_COMBINED_CLIENT_SPLIT);
    }
    
    public static String getAppTag(String jwtToken) {
        return getAppTagAndChannelTag(jwtToken)[0];
    }
    
    public static String getAppChannelTag(String jwtToken) {
        return getAppTagAndChannelTag(jwtToken)[1];
    }

    /**
     * @param key
     * @return
     * @Title: getTokenAdditionalInfo
     * @author klw
     * @Description: 根据key获取token中的额外数据
     */
    public static Object getTokenAdditionalData(String jwtToken, String key) {
        Map<String, Object> allTokenData = getAllTokenData(jwtToken);
        if (allTokenData == null) {
            return null;
        }
        return allTokenData.get(key);
    }

    /**
     * @return
     * @Title: getAllTokenData
     * @author klw
     * @Description: 获取token中的所有数据
     */
    public static Map<String, Object> getAllTokenData(String jwtToken) {
        if (jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring("Bearer ".length());
        }
        String tokenDataPart;
        try {
            tokenDataPart = jwtToken.split("\\.")[1];
        } catch (Exception e) {
            log.info("token 格式不是 JWT");
            return null;
        }
        String json = new String(Base64.getDecoder().decode(tokenDataPart.getBytes()));
        JSONObject obj = JSON.parseObject(json);
        return obj;
    }
    
    public static String removeTokenType(String token){
        if (token.startsWith("Bearer ")) {
            return token.substring("Bearer ".length());
        }
        return token;
    }

}
