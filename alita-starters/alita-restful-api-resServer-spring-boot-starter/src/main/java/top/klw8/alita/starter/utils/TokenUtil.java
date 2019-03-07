package top.klw8.alita.starter.utils;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.http.server.reactive.ServerHttpRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: TokenUtil
 * @Description: token 工具
 * @author klw
 * @date 2018年11月30日 上午11:58:27
 */
@Slf4j
public class TokenUtil {

    /**
     * @Title: getUserId
     * @author klw
     * @Description: 获取token中的userId
     * @return
     */
    public static Long getUserId(String jwtToken) {
	Object userId = getTokenAdditionalData(jwtToken, "userId");
	if(userId instanceof Integer) {
	    return Long.valueOf((Integer)userId);
	}
	return (Long) getTokenAdditionalData(jwtToken, "userId");
    }
    
    /**
     * @Title: getUserId
     * @author klw
     * @Description: 获取token中的userId
     * @param request
     * @return
     */
    public static Long getUserId(ServerHttpRequest request) {
	Object userId = getTokenAdditionalData(request, "userId");
	if(userId instanceof Integer) {
	    return Long.valueOf((Integer)userId);
	}
	return (Long) userId;
    }
    
    /**
     * @Title: getTokenAdditionalInfo
     * @author klw
     * @Description: 根据key获取token中的额外数据
     * @param key
     * @return
     */
    public static Object getTokenAdditionalData(String jwtToken, String key) {
	Map<String, Object> allTokenData = getAllTokenData(jwtToken);
	if(allTokenData == null) {
	    return null;
	}
	return allTokenData.get(key);
    }
    
    /**
     * @Title: getTokenAdditionalData
     * @author klw
     * @Description: 根据key获取token中的额外数据
     * @param request
     * @param key
     * @return
     */
    public static Object getTokenAdditionalData(ServerHttpRequest request, String key) {
	Map<String, Object> allTokenData = getAllTokenData(request);
	if(allTokenData == null) {
	    return null;
	}
	return allTokenData.get(key);
    }
    
    /**
     * @Title: getAllTokenData
     * @author klw
     * @Description: 获取token中的所有数据
     * @return
     */
    public static Map<String, Object> getAllTokenData(String jwtToken){
	if (jwtToken.startsWith("Bearer ")) {
	    jwtToken = jwtToken.substring("Bearer ".length());
	}
	String tokenDataPart = null;
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
    
    /**
     * @Title: getAllTokenData
     * @author klw
     * @Description: 获取token中的所有数据
     * @param request
     * @return
     */
    public static Map<String, Object> getAllTokenData(ServerHttpRequest request){
	List<String> tokenList = request.getHeaders().get("Authorization");
	if(tokenList == null || tokenList.isEmpty()) {
	    return null;
	}
	String jwtToken = tokenList.get(0);
	return getAllTokenData(jwtToken);
    }

}
