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
package top.klw8.alita.entitys.demo.mybatis;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.klw8.alita.entitys.base.BaseEntity;

import java.util.Date;

/**
 * 用户扩展信息表实体
 * 2019-08-19 10:43
 */
@TableName("ext_user_info")
@Getter
@Setter
@ToString
public class ExtUserInfo extends BaseEntity {

    private static final long serialVersionUID = 6884327869246437788L;

    /**
     * 15:45 2019-08-19
     **/
    @TableField("user_id")
    private String userId;

    /**
     * 14:20 2019-08-19
     **/
    @TableField("user_email")
    private String userEmail;

    /**
     * 14:21 2019-08-19
     **/
    @TableField("nick_name")
    private String nickName;

    /**
     * 14:22 2019-08-19
     **/
    @TableField("user_age")
    private Integer userAge;

    /**
     * 14:23 2019-08-19
     **/
    @TableField("user_birth")
    private Date userBirth;

    /**
     * 14:25 2019-08-19
     **/
    @TableField("user_desc")
    private String userDesc;

    /**
     * 14:43 2019-08-19
     **/
    @TableField("user_level")
    private Integer userLevel;

}
