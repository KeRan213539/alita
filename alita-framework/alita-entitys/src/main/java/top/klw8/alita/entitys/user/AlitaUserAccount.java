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
package top.klw8.alita.entitys.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.base.BaseEntity;

/**
 * @author klw
 * @ClassName: AlitaUserAccount
 * @Description: 用户信息表
 * @date 2018年11月9日 下午4:18:46
 */
@TableName("alita_user_account")
@Getter
@Setter
//@EqualsAndHashCode(callSuper = false, exclude = {"userRoles"})
//@ToString(callSuper = false, exclude ={"userRoles"})
public class AlitaUserAccount extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 5844035690295299775L;

    /**
     * @author klw
     * @Fields userName : 用户名(登录帐号)
     */
    @TableField("user_name")
    private String userName;

    /**
     * @author klw
     * @Fields userPhoneNum : 用户手机号
     */
    @TableField("user_phone_num")
    private String userPhoneNum;

    /**
     * @author klw
     * @Fields userPwd : 用户密码
     */
    @TableField(value = "user_pwd")
    private String userPwd;

    /**
     * @author klw
     * @Fields createDate : 创建/注册时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * @author klw
     * @Fields accountNonExpired : 账户是否未过期(true 是未过期)
     */
    @TableField("account_non_expired")
    @JsonIgnore
    private Boolean accountNonExpired1;

    /**
     * @author klw
     * @Fields accountNonLocked : 账户是否未锁定 (true 是未锁定)
     */
    @TableField("account_non_locked")
    @JsonIgnore
    private Boolean accountNonLocked1;

    /**
     * @author klw
     * @Fields credentialsNonExpired : 用户密码是否未过期(true 是未过期), 密码过期了会登录失败(需要强制用户修改密码)
     */
    @TableField("credentials_non_expired")
    @JsonIgnore
    private Boolean credentialsNonExpired1;

    /**
     * @author klw
     * @Fields enabled : 账户是否启用(true 是启用)
     */
    @TableField("enabled")
    @JsonIgnore
    private Boolean enabled1;

    /**
     * @author klw
     * @Fields userRoles : 用户拥有的角色和权限
     */
    @TableField(exist=false)
    private List<SystemRole> userRoles;

    public AlitaUserAccount() {
        // 序列化用的构造方法
    }

    public AlitaUserAccount(String userName, String userPhoneNum, String userPwd) {
        this.userName = userName;
        this.userPhoneNum = userPhoneNum;
        this.userPwd = userPwd;
        this.accountNonExpired1 = Boolean.TRUE;
        this.accountNonLocked1 = Boolean.TRUE;
        this.credentialsNonExpired1 = Boolean.TRUE;
        this.enabled1 = Boolean.TRUE;
    }

    public AlitaUserAccount initNewAccount(){
        this.accountNonExpired1 = Boolean.TRUE;
        this.accountNonLocked1 = Boolean.TRUE;
        this.credentialsNonExpired1 = Boolean.TRUE;
        this.enabled1 = Boolean.TRUE;
        this.setCreateDate(LocalDateTime.now());
        return this;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> rList = new ArrayList<>(1);
//        rList.add(new GrantedAuthority() {
//            private static final long serialVersionUID = 5844035690295299785L;
//            @Override
//            public String getAuthority() {
//                // 权限管理不使用 spirng security,而是自己实现,所以这里返回什么无所谓了
//                return "user";
//            }
//        });
        return new ArrayList<>(0);
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.userPwd;
    }

    @Override
    @JsonProperty("userName")
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired1;
    }

    @Override
    public boolean isEnabled() {
        return enabled1;
    }

}
