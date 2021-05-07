package top.klw8.alita.entitys.authority.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * 资源类型枚举.
 *
 * 2021/4/9 14:35
 */
public enum ResourceType implements IEnum {

    /**
     * 按钮资源(权限).
     */
    BUTTON,

    /**
     * 数据资源(权限).
     */
    DATA,
    ;

    @Override
    public Serializable getValue() {
        return this.name();
    }
}
