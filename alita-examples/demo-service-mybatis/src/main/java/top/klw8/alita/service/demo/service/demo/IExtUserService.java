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
package top.klw8.alita.service.demo.service.demo;

import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.demo.mybatis.ExtUserInfo;

import java.util.List;

/**
 * 用户扩展信息服务定义
 * 2019-08-19 15:14
 */
public interface IExtUserService extends IService<ExtUserInfo> {

    /**
     * 添加新的用户扩展信息
     * 16:35 2019-08-20
     * @param: extUserInfo
     * @return boolean
     **/
    boolean addUserExtInfo(ExtUserInfo extUserInfo);

    /**
     * 删除用户的扩展信息
     * 16:35 2019-08-20
     * @param: userId
     * @return boolean
     **/
    boolean deleteUserExtInfo(String userId);

    /**
     * 更新用户的扩展信息
     * 16:36 2019-08-20
     * @param: extUserInfo
     * @return boolean
     **/
    boolean updateUserExtInfo(ExtUserInfo extUserInfo);

    /**
     * 根据用户ID查询用户的扩展信息
     * 15:41 2019-08-19
     * @param: userId
     * @return top.klw8.alita.entitys.demo.mybatis.ExtUserInfo
     **/
    ExtUserInfo getExtByUserId(String userId);

    /**
     * 根据用户的email查询用户的扩展信息
     * 15:42 2019-08-19
     * @param: userEmail
     * @return top.klw8.alita.entitys.demo.mybatis.ExtUserInfo
     **/
    ExtUserInfo getExtByEmail(String userEmail);

    /**
     * 根据等级查询符合条件的所有扩展信息
     * 15:44 2019-08-19
     * @param: level
     * @return java.util.List<top.klw8.alita.entitys.demo.mybatis.ExtUserInfo>
     **/
    List<ExtUserInfo> getExtsByLevel(Integer level);

}
