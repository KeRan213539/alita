package top.klw8.alita.entitys.authority;


import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.service.base.entitys.BaseEntity;

/**
 * @ClassName: SystemAuthoritysCatlog
 * @Description: 权限的目录
 * @author klw
 * @date 2018年11月28日 上午11:53:50
 */
@Document(collection = "sys_authoritys_catlog")
@Getter
@Setter
//@EqualsAndHashCode(callSuper = false, exclude = {"authorityList"})
//@ToString(callSuper = false, exclude ={"authorityList"})
public class SystemAuthoritysCatlog extends BaseEntity {

    private static final long serialVersionUID = -8415317762418810314L;

    /**
     * @author klw
     * @Fields catlogName : 目录名称
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
    
}
