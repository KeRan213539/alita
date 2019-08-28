package top.klw8.alita.demo.web.demo;

import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.klw8.alita.demo.web.demo.mybatisvo.ExtUserInfoVo;
import top.klw8.alita.entitys.demo.mybatis.ExtUserInfo;
import top.klw8.alita.service.demo.providers.user.IExtUserProvider;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.CommonResultCodeEnum;

import java.util.concurrent.ExecutionException;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: MybatisDemoController
 * @Description: mybatis demo
 * @date 2019/8/8 15:47
 */
@Api(tags = {"alita-restful-API--demoAPI"})
@RestController
@RequestMapping("${spring.application.name}/demo")
@Slf4j
public class MybatisDemoController {

    @Reference(async = true)
    private IExtUserProvider extUserProvider;

    @ApiOperation(value = "添加新的用户扩展信息", notes = "添加新的用户扩展信息", httpMethod = "POST", produces = "application/json")
    @PostMapping("/addext")
    public Mono<JsonResult> addUserExt(@RequestBody ExtUserInfoVo vo) {
        if (null == vo) {
            return Mono.just(JsonResult.sendFailedResult(CommonResultCodeEnum.BAD_PARAMETER));
        } else {
            ExtUserInfo info = new ExtUserInfo();
            BeanUtils.copyProperties(vo, info);
            return Mono.fromFuture(extUserProvider.addUserExtInfo(info));
        }
    }

    @ApiOperation(value = "删除用户扩展信息", notes = "删除用户扩展信息", httpMethod = "POST", produces = "application/json")
    @PostMapping("/delext")
    public Mono<JsonResult> deleteUserExt(@RequestBody ExtUserInfoVo vo) {
        if (null == vo && StringUtil.isNullOrEmpty(vo.getUserId())) {
            return Mono.just(JsonResult.sendFailedResult(CommonResultCodeEnum.BAD_PARAMETER));
        } else {
            return Mono.fromFuture(extUserProvider.deleteUserExtInfo(vo.getUserId()));
        }
    }

    @ApiOperation(value = "更新用户扩展信息", notes = "更新用户扩展信息", httpMethod = "POST", produces = "application/json")
    @PostMapping("/updateext")
    public Mono<JsonResult> updateUserExt(@RequestBody ExtUserInfoVo vo) {
        if (null == vo && StringUtil.isNullOrEmpty(vo.getUserId())) {
            return Mono.just(JsonResult.sendFailedResult(CommonResultCodeEnum.BAD_PARAMETER));
        } else {
            ExtUserInfo info = new ExtUserInfo();
            BeanUtils.copyProperties(vo, info);
            return Mono.fromFuture(extUserProvider.updateUserExtInfo(info));
        }
    }

    @ApiOperation(value = "查找用户扩展信息", notes = "查找用户扩展信息", httpMethod = "POST", produces = "application/json")
    @PostMapping("/findextbyuser")
    public Mono<JsonResult> findUserExtByUser(@RequestBody ExtUserInfoVo vo) throws ExecutionException, InterruptedException {
        // 首先判断一下是否传入了需要的参数
        if (StringUtil.isNullOrEmpty(vo.getUserId())) {
            return Mono.just(JsonResult.sendFailedResult("调用失败"));
        } else {
            return Mono.fromFuture(extUserProvider.findExtByUserId(vo.getUserId()));
        }
    }

    @ApiOperation(value = "查找某个等级的所有用户扩展信息", notes = "查找某个等级的所有用户扩展信息", httpMethod = "POST", produces = "application/json")
    @PostMapping("/findextsbylevel")
    public Mono<JsonResult> findExtsByLevel(@RequestBody ExtUserInfoVo vo) {
        // 首先判断一下是否传入了需要的参数
        if (null == vo.getUserLevel() || vo.getUserLevel().intValue() <= 0) {
            return Mono.just(JsonResult.sendFailedResult("调用失败"));
        } else {
            return Mono.fromFuture(extUserProvider.findExtsByLevel(vo.getUserLevel()));
        }
    }

}
