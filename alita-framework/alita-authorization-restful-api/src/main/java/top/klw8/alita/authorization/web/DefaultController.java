package top.klw8.alita.authorization.web;


import static top.klw8.alita.authorization.common.SysContext.ONE_MINUTE;
import static top.klw8.alita.authorization.common.SysContext.SMS_CODE_CACHE_PREFIX;
import static top.klw8.alita.authorization.common.SysContext.SMS_CODE_TIMEOUT_MSG;
import static top.klw8.alita.authorization.common.SysContext.SMS_CODE_TIMEOUT_SECOND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import top.klw8.alita.authorization.common.AlitaUserDetailsService;
import top.klw8.alita.authorization.web.vo.SendSMSCodeRequest;
import top.klw8.alita.starter.web.base.WebapiBaseController;
import top.klw8.alita.starter.web.common.JsonResult;
import top.klw8.alita.utils.generator.SecurityCodeGenerator;
import top.klw8.alita.utils.redis.RedisTagEnum;
import top.klw8.alita.utils.redis.RedisUtil;
import top.klw8.alita.validator.UseValidator;

/**
 * @ClassName: DefaultController
 * @Description: 扩展认证中心
 * @author klw
 * @date 2018年11月21日 上午11:03:31
 */
@Api(tags = {"alita-restful-API--认证中心API"})
@RestController
@RequestMapping("/oauth")
@RefreshScope
public class DefaultController extends WebapiBaseController {
    
    
    @Autowired
    private AlitaUserDetailsService userService;
    
    @ApiOperation(value = "发送短信验证码", notes = "发送短信验证码", httpMethod = "POST", produces = "application/json")
//    @ApiImplicitParams({
//	@ApiImplicitParam(paramType = "query", dataType = "String", name = "userMobileNo", value = "用户手机号", required = true) 
//    })
    @PostMapping("/sendSmsCode")
    @UseValidator
    public JsonResult sendSmsCode(SendSMSCodeRequest prarm) {
	String userMobileNo = prarm.getUserMobileNo();
	// 检查是否有上次发送过的验证码
	String smsCode = (String) RedisUtil.get(SMS_CODE_CACHE_PREFIX + userMobileNo,
		RedisTagEnum.REDIS_TAG_DEFAULT);
	if (smsCode != null) {
	    // 检查距离上次发送是否小于1分钟
	    Long sendTime = (Long) RedisUtil.get(SMS_CODE_CACHE_PREFIX + "SEND_TIME_" + userMobileNo,
		    RedisTagEnum.REDIS_TAG_DEFAULT);
	    if (sendTime != null
		    && (System.currentTimeMillis() - sendTime.longValue()) < ONE_MINUTE) {
		return JsonResult.sendFailedResult("距上次发送不足1分钟,请不要重复发送", null);
	    }
	    // TODO 发送验证码
	    System.out.println("================" + userMobileNo + "====" + smsCode + "====请在" + SMS_CODE_TIMEOUT_MSG + "内使用");
	    return JsonResult.sendSuccessfulResult("验证码发送成功", null);
	}
	
	// 到这里说明是第一次发送,先检查用户是否存在
	try {
	    userService.loadUserByUsername(userMobileNo);
	} catch (UsernameNotFoundException e) {
	    return JsonResult.sendFailedResult("用户不存在", null);
	}
	smsCode = SecurityCodeGenerator.numberCode6();
	
	// 验证码和发送时间放缓存
	RedisUtil.set(SMS_CODE_CACHE_PREFIX + userMobileNo, smsCode, SMS_CODE_TIMEOUT_SECOND, RedisTagEnum.REDIS_TAG_DEFAULT); 
	RedisUtil.set(SMS_CODE_CACHE_PREFIX + "SEND_TIME_" + userMobileNo, System.currentTimeMillis(), 60L, RedisTagEnum.REDIS_TAG_DEFAULT); // 上次发送时间只放1分钟,redis里拿不到说明已经超过1分钟
	
	// TODO 发送验证码
	System.out.println("================" + userMobileNo + "====" + smsCode + "====请在" + SMS_CODE_TIMEOUT_MSG + "内使用");
	return JsonResult.sendSuccessfulResult("验证码发送成功", null);
    }
    
}