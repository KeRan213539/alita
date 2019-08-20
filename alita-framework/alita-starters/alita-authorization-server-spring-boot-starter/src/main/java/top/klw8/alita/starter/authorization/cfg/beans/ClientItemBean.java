package top.klw8.alita.starter.authorization.cfg.beans;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName: ClientItemBean
 * @Description: OAuth2客户端配制bean
 * @author klw
 * @date 2018年12月13日 上午11:24:16
 */
@Getter
@Setter
public class ClientItemBean{
	
	/**
	 * @author klw
	 * @Fields clientId : 客户端ID
	 */
	private String clientId;
	
	/**
	 * @author klw
	 * @Fields clientSecret : 密码
	 */
	private String clientSecret;
	
	/**
	 * @author klw
	 * @Fields scope : 客户端能操作的范围(暂时没有验证此属性,随便给个就行)
	 */
	private List<String> scope;
	
	/**
	 * @author klw
	 * @Fields authorizedGrantTypes : 客户端可以调用的认证中心接口
	 */
	private List<String> authorizedGrantTypes;
	
	/**
	 * @author klw
	 * @Fields authority : 权限(暂时没有验证此属性,随便给个就行)
	 */
	private List<String> authorities;
}
