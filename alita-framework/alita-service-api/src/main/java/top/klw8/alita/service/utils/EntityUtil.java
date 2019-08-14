package top.klw8.alita.service.utils;

import org.apache.commons.lang3.StringUtils;
import top.klw8.alita.entitys.base.BaseEntity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


/**
 * @ClassName: EntityUtil
 * @Description: 实体工具
 * @author klw
 * @date 2018年12月20日 下午2:26:43
 */
public class EntityUtil {

    /**
     * @Title: isEntityEmpty
     * @author klw
     * @Description: 检查实体是否是空的
     * @param baseBean
     * @return
     */
    public static boolean isEntityEmpty(BaseEntity baseBean) {
	return baseBean == null ? true : StringUtils.isBlank(baseBean.getId());
    }
    
    /**
     * @Title: isEntityNotEmpty
     * @author klw
     * @Description: 检查实体是否不是空的
     * @param baseBean
     * @return
     */
    public static boolean isEntityNotEmpty(BaseEntity baseBean) {
	return !isEntityEmpty(baseBean);
    }
    
    /**
     * @Title: isEntityCanModify
     * @author klw
     * @Description: 判断实体是否能用于修改的保存
     * @param baseBean
     * @return true: 可以用于修改
     * 		false: 实体中只有ID其他都为null,或者ID都没有,不能用于修改 <br />
     */
    public static <T extends BaseEntity> boolean isEntityCanModify(T baseBean) {
	if (baseBean.getId() == null) {
	    return false;
	}
	boolean isAllNull = true;
	List<Field> fieldList = Arrays.asList(baseBean.getClass().getDeclaredFields());
	for (Field field : fieldList) {
	    if(field.getName().equals("serialVersionUID")) {
		continue;
	    }
	    if(field.getName().equals("id")) {
		continue;
	    }
	    field.setAccessible(true);
	    Object value = null;
	    try {
		value = field.get(baseBean);
	    } catch (IllegalArgumentException | IllegalAccessException e) {
		e.printStackTrace();
	    }
	    if(value != null) {
		isAllNull = false;
	    }
	}
	return !isAllNull;
    }

}
