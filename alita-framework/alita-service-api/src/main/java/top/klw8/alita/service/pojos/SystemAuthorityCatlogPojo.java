package top.klw8.alita.service.pojos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SystemAuthorityCatlogPojo
 * @Description: 返回给视图的菜单目录
 * @date 2019/10/18 10:25
 */
@Getter
@Setter
@ToString
public class SystemAuthorityCatlogPojo implements java.io.Serializable, Comparable<SystemAuthorityCatlogPojo> {

    private static final long serialVersionUID = 3822658664344722701L;
    /**
     * @author klw
     * @Fields id : 主键ID
     */
    private String id;

    /**
     * @author klw
     * @Fields roleName : 角色名称
     */
    private String catlogName;

    /**
     * @author klw
     * @Fields showIndex : 显示顺序
     */
    private Integer showIndex;

    /**
     * @author klw
     * @Fields remark : 备注
     */
    private String remark;

    /**
     * @author klw
     * @Fields authorityList : 角色下的权限
     */
    private List<SystemAuthorityPojo> authorityList;

    @Override
    public int compareTo(SystemAuthorityCatlogPojo o) {
        return this.showIndex - o.showIndex;
    }
}
