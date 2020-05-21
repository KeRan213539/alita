package top.klw8.alita.service.demo.service.demo;

import com.baomidou.mybatisplus.extension.service.IService;
import top.klw8.alita.entitys.demo.mybatis.ExtUserInfo;

import java.util.List;

/**
 * @ClassName IExtUserService
 * @Description 用户扩展信息服务定义
 * @Author ZhangLei
 * @Date 2019-08-19 15:14
 * @Version 1.0
 */
public interface IExtUserService extends IService<ExtUserInfo> {

    /**
     * @Author zhanglei
     * @Description 添加新的用户扩展信息
     * @Date 16:35 2019-08-20
     * @param: extUserInfo
     * @return boolean
     **/
    boolean addUserExtInfo(ExtUserInfo extUserInfo);

    /**
     * @Author zhanglei
     * @Description 删除用户的扩展信息
     * @Date 16:35 2019-08-20
     * @param: userId
     * @return boolean
     **/
    boolean deleteUserExtInfo(String userId);

    /**
     * @Author zhanglei
     * @Description 更新用户的扩展信息
     * @Date 16:36 2019-08-20
     * @param: extUserInfo
     * @return boolean
     **/
    boolean updateUserExtInfo(ExtUserInfo extUserInfo);

    /**
     * @Author zhanglei
     * @Description 根据用户ID查询用户的扩展信息
     * @Date 15:41 2019-08-19
     * @param: userId
     * @return top.klw8.alita.entitys.demo.mybatis.ExtUserInfo
     **/
    ExtUserInfo getExtByUserId(String userId);

    /**
     * @Author zhanglei
     * @Description 根据用户的email查询用户的扩展信息
     * @Date 15:42 2019-08-19
     * @param: userEmail
     * @return top.klw8.alita.entitys.demo.mybatis.ExtUserInfo
     **/
    ExtUserInfo getExtByEmail(String userEmail);

    /**
     * @Author zhanglei
     * @Description 根据等级查询符合条件的所有扩展信息
     * @Date 15:44 2019-08-19
     * @param: level
     * @return java.util.List<top.klw8.alita.entitys.demo.mybatis.ExtUserInfo>
     **/
    List<ExtUserInfo> getExtsByLevel(Integer level);

}