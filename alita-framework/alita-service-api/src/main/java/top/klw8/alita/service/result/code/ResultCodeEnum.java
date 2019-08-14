package top.klw8.alita.service.result.code;

/**
 * @ClassName: ResultCodeEnum
 * @Description: JsonResultCode 枚举
 * @author klw
 * @date 2018年12月7日 下午1:53:11
 */
public enum ResultCodeEnum {

    /**
     * @author klw
     * @Fields _200 : 处理成功
     */
    _200(200),
    
    /**
     * @author klw
     * @Fields _250 : token失效
     */
    _250(250),
    
    /**
     * @author klw
     * @Fields _400 : 参数错误
     */
    _400(400),
    
    /**
     * @author klw
     * @Fields _401 : 未登录
     */
    _401(401),
    
    /**
     * @author klw
     * @Fields _403 : 禁止访问(没有权限)
     */
    _403(403),
    
    /**
     * @author klw
     * @Fields _500 : 服务器内部错误
     */
    _500(500)
    
    ;
    private int value;
    
    private ResultCodeEnum(int value) {
	this.value = value;
    }
    
    public int intValue() {
	return value;
    }
    
}
