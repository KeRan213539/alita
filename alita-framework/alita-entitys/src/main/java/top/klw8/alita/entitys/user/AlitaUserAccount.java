package top.klw8.alita.entitys.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.base.BaseEntity;

/**
 * @ClassName: AlitaUserAccount
 * @Description: 用户信息表,包括前台用户,后台用户
 * @author klw
 * @date 2018年11月9日 下午4:18:46
 */
@Document(collection = "alita_user_account")
@Getter
@Setter
//@EqualsAndHashCode(callSuper = false, exclude = {"userRoles"})
//@ToString(callSuper = false, exclude ={"userRoles"})
public class AlitaUserAccount extends BaseEntity implements UserDetails {
    
    private static final long serialVersionUID = 5844035690295299775L;
    
    /**
     * @author klw
     * @Fields userPhoneNum : 用户手机号
     */
    @Indexed(unique=true)
    private String userPhoneNum;
    
    /**
     * @author klw
     * @Fields userPwd : 用户密码
     */
    private String userPwd;
    
    /**
     * @author klw
     * @Fields userTypeId : 类型对应的表主键
     */
    private ObjectId userTypeId;
    
    /**
     * @author klw
     * @Fields weChatOpenId : 微信的 OpenId
     */
    @Indexed
    private String weChatOpenId;
    
    /**
     * @author klw
     * @Fields createDate : 创建/注册时间
     */
    private LocalDateTime createDate;
    
    /**
     * @author klw
     * @Fields userRoles : 用户拥有的角色和权限
     */
    @DBRef
    @Indexed
    private List<SystemRole> userRoles;
    
    /**
     * @author klw
     * @Fields accountNonExpired : 账户是否未过期(true 是未过期)
     */
    private Boolean accountNonExpired;
    
    /**
     * @author klw
     * @Fields accountNonLocked : 账户是否未锁定 (true 是未锁定)
     */
    private Boolean accountNonLocked;
    
    /**
     * @author klw
     * @Fields credentialsNonExpired : 用户密码是否未过期(true 是未过期), 密码过期了会登录失败(需要强制用户修改密码)"
     */
    private Boolean credentialsNonExpired;
    
    /**
     * @author klw
     * @Fields enabled : 账户是否启用(true 是启用)
     */
    private Boolean enabled;
    
    public AlitaUserAccount() {}
    
    public AlitaUserAccount(String userPhoneNum, String userPwd) {
	this.userPhoneNum = userPhoneNum;
	this.userPwd = userPwd;
	this.accountNonExpired = Boolean.TRUE;
	this.accountNonLocked = Boolean.TRUE;
	this.credentialsNonExpired = Boolean.TRUE;
	this.enabled = Boolean.TRUE;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
	List<GrantedAuthority> rList = new ArrayList<>(1);
	rList.add(new GrantedAuthority() {
	    private static final long serialVersionUID = 5844035690295299785L;
	    @Override
	    public String getAuthority() {
		// 权限管理不使用 spirng security,而是自己实现,所以这里返回什么无所谓了
		return "user";
	    }
	});
	return rList;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
	return this.userPwd;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
	return this.userPhoneNum;
    }

    @Override
    public boolean isAccountNonExpired() {
	return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
	return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
	return enabled;
    }

}
