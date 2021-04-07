/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.service.utils;

import org.apache.commons.lang3.StringUtils;
import top.klw8.alita.entitys.base.BaseEntity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


/**
 * @author klw
 * @ClassName: EntityUtil
 * @Description: 实体工具
 * @date 2018年12月20日 下午2:26:43
 */
public class EntityUtil {

    /**
     * @param baseBean
     * @return
     * @Title: isEntityEmpty
     * @author klw
     * @Description: 检查实体是否没有主键
     */
    public static boolean isEntityNoId(BaseEntity baseBean) {
        return baseBean == null ? true : StringUtils.isBlank(baseBean.getId());
    }

    /**
     * @param baseBean
     * @return
     * @Title: isEntityNotEmpty
     * @author klw
     * @Description: 检查实体是否有主键
     */
    public static boolean isEntityHasId(BaseEntity baseBean) {
        return !isEntityNoId(baseBean);
    }

    /**
     * @param baseBean
     * @return true: 可以用于修改
     * false: 实体中只有ID其他都为null,或者ID都没有,不能用于修改 <br />
     * @Title: isEntityCanModify
     * @author klw
     * @Description: 判断实体是否能用于修改的保存
     */
    public static <T extends BaseEntity> boolean isEntityCanModify(T baseBean) {
        if (baseBean.getId() == null) {
            return false;
        }
        boolean     isAllNull = true;
        List<Field> fieldList = Arrays.asList(baseBean.getClass().getDeclaredFields());
        for (Field field : fieldList) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            if (field.getName().equals("id")) {
                continue;
            }
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(baseBean);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null) {
                isAllNull = false;
            }
        }
        return !isAllNull;
    }

}
