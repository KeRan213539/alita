package top.klw8.alita.entitys.authority.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

/**
 * @ClassName: AuthorityType
 * @Description: 权限类型
 * @author klw
 * @date 2018年11月28日 下午3:01:49
 */
public enum AuthorityTypeEnum implements IEnum {

    /**
     * @author klw
     * @Fields MENU : 菜单(作为菜单显示)
     */
    MENU,
    
    /**
     * @author klw
     * @Fields URL : URL
     */
    URL;

    @Override
    public Serializable getValue() {
        return this.name();
    }
}
