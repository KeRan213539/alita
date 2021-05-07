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
package top.klw8.test.reflection;

import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.utils.UUIDUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 获取类的字段测试
 * 2018年10月1日 下午4:54:49
 */
public class GetFieldTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        AlitaUserAccount bean = new AlitaUserAccount();
        bean.setId(UUIDUtil.getRandomUUIDString());
        bean.setUserPhoneNum("138888888888");
        bean.setUserPwd("xxxxxxx");

        List<Field> fieldList = new ArrayList<Field>(Arrays.asList(bean.getClass().getDeclaredFields()));
        for (Field field : fieldList) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            field.setAccessible(true);
            Object obj = field.get(bean);
            if (obj != null) {
                System.out.println(field.getName() + " = " + obj);
            }
        }
    }

}
