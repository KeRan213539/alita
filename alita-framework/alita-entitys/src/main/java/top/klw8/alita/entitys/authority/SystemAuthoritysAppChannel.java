/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
 * @author klw(213539 @ qq.com)
 * @ClassName: SystemAuthoritysAppChannel
 * @date 2020/9/9 16:51
 */
@TableName(value = "sys_authoritys_app_channel")
@Getter
@Setter
@ToString
public class SystemAuthoritysAppChannel implements Serializable, Cloneable {
    
    private static final long serialVersionUID = -3145989952679035685L;
    
    /**
     * @author klw(213539@qq.com)
     * @Description: 渠道标识(主键)
     */
    @TableId(type = IdType.INPUT)
    @TableField("channel_tag")
    private String channelTag;
    
    /**
     * @author klw(213539@qq.com)
     * @Description: 渠道所属应用的标识
     */
    @TableField("app_tag")
    private String appTag;
    
    /**
     * 渠道密码(明文)
     * @author klw(213539@qq.com)
     */
    @TableField("channel_pwd")
    private String channelPwd;
    
    /**
     * 渠道支持的登录方式,多种方式用逗号隔开
     * @author klw(213539@qq.com)
     */
    @TableField("channel_login_type")
    private String channelLoginType;
    
    /**
     * @author klw(213539@qq.com)
     * @Description: 备注
     */
    @TableField("remark")
    private String remark;

}
