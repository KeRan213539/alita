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
package top.klw8.alita.entitys.authority;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 应用渠道表.
 *
 * 2020/9/9 16:51
 */
@TableName(value = "alita_authoritys_app_channel")
@Getter
@Setter
@ToString
public class AlitaAuthoritysAppChannel implements Serializable, Cloneable {
    
    private static final long serialVersionUID = -3145989952679035685L;
    
    /**
     * 渠道标识(主键)
     */
    @TableId(type = IdType.INPUT)
    @TableField("channel_tag")
    private String channelTag;
    
    /**
     * 渠道所属应用的标识
     */
    @TableField("app_tag")
    private String appTag;
    
    /**
     * 渠道密码(明文)
     */
    @TableField("channel_pwd")
    private String channelPwd;
    
    /**
     * 渠道支持的登录方式,多种方式用逗号隔开
     */
    @TableField("channel_login_type")
    private String channelLoginType;
    
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
