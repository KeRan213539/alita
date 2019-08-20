package top.klw8.alita.service.mybatisdemo.services.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import top.klw8.alita.entitys.demo.mybatis.ExtUserInfo;
import top.klw8.alita.service.mybatisdemo.services.user.IExtUserService;
import top.klw8.alita.service.mybatisdemo.services.user.mapper.IExtUserMapper;

import java.util.List;

/**
 * @author freedom
 * @version 1.0
 * @ClassName ExtUserServiceImpl
 * @Description 用户扩展信息服务实现
 * @date 2019-08-19 15:30
 */
@Slf4j
@Service
public class ExtUserServiceImpl extends ServiceImpl<IExtUserMapper, ExtUserInfo> implements IExtUserService {

    @Override
    public ExtUserInfo getExtByUserId(String userId) {
        QueryWrapper<ExtUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.getOne(queryWrapper);
    }

    @Override
    public ExtUserInfo getExtByEmail(String userEmail) {
        QueryWrapper<ExtUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", userEmail);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<ExtUserInfo> getExtsByLevel(Integer level) {
        QueryWrapper<ExtUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_level", level);
        return this.list(queryWrapper);
    }

}
