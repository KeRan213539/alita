package top.klw8.alita.service.demo.providers.impl.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import top.klw8.alita.entitys.demo.mybatis.ExtUserInfo;
import top.klw8.alita.service.demo.providers.user.IExtUserProvider;
import top.klw8.alita.service.demo.service.demo.IExtUserService;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;
import top.klw8.alita.service.utils.EntityUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author freedom
 * @version 1.0
 * @ClassName ExtUserProvider
 * @Description 用户扩展信息dubbo提供者实现
 * @date 2019-08-19 14:50
 */
@Slf4j
@Service(async = true)
public class ExtUserProvider implements IExtUserProvider {

    @Autowired
    private IExtUserService extUserService;

    @Override
    public CompletableFuture<JsonResult> addUserExtInfo(ExtUserInfo extUserInfo) {
        return CompletableFuture.supplyAsync(() -> {
            if (extUserService.addUserExtInfo(extUserInfo)) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult(CommonResultCodeEnum.ERROR, "添加失败");
            }
        });
    }

    @Override
    public CompletableFuture<JsonResult> deleteUserExtInfo(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            if (extUserService.deleteUserExtInfo(userId)) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult(CommonResultCodeEnum.ERROR, "删除失败");
            }
        });
    }

    @Override
    public CompletableFuture<JsonResult> updateUserExtInfo(ExtUserInfo extUserInfo) {
        return CompletableFuture.supplyAsync(() -> {
            if (extUserService.updateUserExtInfo(extUserInfo)) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult(CommonResultCodeEnum.ERROR, "更新失败");
            }
        });
    }

    @Override
    public CompletableFuture<JsonResult> findExtByUserId(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            ExtUserInfo extUserInfo = extUserService.getExtByUserId(userId);
            if (null != extUserInfo && !EntityUtil.isEntityNoId(extUserInfo)) {
                return JsonResult.sendSuccessfulResult(extUserInfo);
            } else {
                return JsonResult.sendFailedResult("未找到");
            }
        });
    }

    @Override
    public CompletableFuture<JsonResult> findExtByEmail(String userEmail) {
        return CompletableFuture.supplyAsync(() -> {
            ExtUserInfo extUserInfo = extUserService.getExtByEmail(userEmail);
            if (null != extUserInfo && !EntityUtil.isEntityNoId(extUserInfo)) {
                return JsonResult.sendSuccessfulResult(extUserInfo);
            } else {
                return JsonResult.sendFailedResult("未找到");
            }
        });
    }

    @Override
    public CompletableFuture<JsonResult> findExtsByLevel(Integer userLevel) {
        return CompletableFuture.supplyAsync(() -> {
            List<ExtUserInfo> extUserInfos = extUserService.getExtsByLevel(userLevel);
            if (null != extUserInfos && !extUserInfos.isEmpty()) {
                return JsonResult.sendSuccessfulResult(extUserInfos);
            } else {
                return JsonResult.sendFailedResult("未找到");
            }
        });
    }

}