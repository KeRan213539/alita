package top.klw8.alita.service.pojos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SystemDataSecured
 * @Description: 给视图用的数据权限pojo, 可以标识用户是否拥有该权限
 * @date 2020-05-15 10:47:43
 */
@Getter
@Setter
@ToString
public class SystemDataSecuredPojo implements java.io.Serializable {

    private static final long serialVersionUID = 4226666111547632645L;

    /**
     * @author klw
     * @Fields id : 主键ID
     */
    private String id;

    /**
     * @author klw(213539@qq.com)
     * @Description: 所属权限ID
     */
    private String authoritysId;

    /**
     * @author klw(213539@qq.com)
     * @Description: 资源标识
     */
    private String resource;

    /**
     * @author klw(213539@qq.com)
     * @Description: 备注/名称
     */
    private String remark;

    /**
     * @author klw(213539@qq.com)
     * @Description: 当前用户是否拥有该权限
     */
    private boolean currUserHas = false;

}
