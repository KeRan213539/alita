package top.klw8.alita.starter.authorization.cfg.beans;

import lombok.Getter;
import lombok.Setter;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: TokenTimeoutConfigBean
 * @Description: token 超时配制bean
 * @date 2020/8/17 11:20
 */
@Getter
@Setter
public class TokenTimeoutConfigBean {

    /**
     * @author klw(213539@qq.com)
     * @Description: access token 超时时间(秒) 默认12小时
     */
    private int access = 43200;

    /**
     * @author klw(213539@qq.com)
     * @Description: refresh token 超时时间(秒) 默认30天
     */
    private int refresh = 2592000;

}
