package top.klw8.alita.config;

public interface SysConstants {
	/**
	 * 分页-每页显示的条数
	 */
	int PAGE_LIST_SIZE = 25;

	//------------sms---------------
	/**
	 * 短信验证码-微信用户绑定
	 */
	int SMS_TYPE_WECHAT_BIND = 1;

	/**
	 * 短信验证码-管家登录
	 */
	int SMS_TYPE_HOUSEKEEPER_LOGIN = 2;

	/**
	 * 短信验证码-管家登录
	 */
	int SMS_TYPE_CUSTOMER_LOGIN = 3;

	/**
	 * 验证码超时
	 */
	int SMS_VER_OVERTIME = -1;

	/**
	 * 验证码验证失败
	 */
	int SMS_VER_FAIL = -2;

	/**
	 * 用户初始密码
	 */
	String INITIAL_PASSWORD = "123";

	//**---------------优惠券---------------**//
	String WALLET_COUPON_TYPE = "coupon";

	//客户钱包优惠券名称
	String WALLET_COUPON_NAME = "优惠券";

	String COUPON_OVERDUE = "不可用：此券已过期";

	String ALREADY_USE = "不可用：此券已使用";

	String COUPON_NOT_USE = "不可用：所有结算商品中没有适合此券的商品";

	String COUPON_NOT_SATISFY = "不可用：所结算商品中没有达到此优惠条件的商品";

	String COUPON_CHEAPEST = "最省";

	//**-------------套餐卡-------------------**//
	String WALLET_MEAL_TYPE = "meal";

	//客户钱包套餐卡名称
	String WALLET_MEAL_NAME = "套餐卡";

	String MEAL_NOT_SATISFY = "不可用：所有结算商品中没有适合此卡的商品";

	String MEAL_OVERDUE = "不可用：此卡已过期";

	String MEAL_HAS_USED = "此卡已过期，剩余使用次数0次";

	String MEAL_PAST = "不可用：此卡激活期限为%1$s,已过激活期限";

	String MEAL_NOT_ACTIVIE = "此卡还未激活";

	String MEAL_FINISHED = "不可用：此卡已用完";

	String MEAL_ADDRESS_FAILED = "不可用：此卡绑定的地址为%1$s";

	String MEAL_TIP_MESSAGE_TEMPLATE = "此卡有%1$s次服务将于%2$s作废，请尽快使用";

	String MEAL_IN_USE = "此卡正在使用中";

	String MEAL_WILL_PAST = "此卡激活期限为%1$s，请尽快激活使用";

	String MEAL_SUGGEST_MESSAGE = "此卡快到期，建议优先使用";

	String MEAL_REMAINDTIMES_NOT_ENOUGH = "卡剩余次数不足已支付该次订单";

	// -------消息中心
	String MEAL_PRODUCT_MESSAGE = "您办理了%1$s一张，可享有一年%2$s次服务，您可在“我的-会员卡”中查看";


	//套餐卡-单次卡type
	Integer MEAL_TYPE = 2;

	Integer SINGLE_TYPE = 1;

	/**
	 * sourceTable对应
	 */
	String ACTIVITY_SPEND = "ActivitySpendDefine";

	String ACTIVITY_RESEARCH = "ActivityResearchDefine";

	String ACTIVITY_RUSH = "ActivityRushDefine";

	String ACTIVITY_SHARE = "ActivityShareDefine";

	String ACTIVITY_TEAM = "ActivityTeamDefine";


	/**----------------产品相关常量-----------------**/
	//系统参数redis中前缀
	String SYSTEM_PARAM_REDIS_PREFIX = "systemParam";

	//作废单位:月
	String INVALID_UNIT_MONTH = "month";

	//作废单位:日
	String INVALID_UNIT_DAY = "day";

	//单次卡，服务，活动前缀
	String REDIS_CONSTANTS_PREFIX = "bean";

	//服务
	String SERVICE_SIGN = "service";

	//套餐
	String MEAL_SIGN = "meal";

	//单次卡
	String SINGLE_CARD_SIGN = "singleCard";

	//请假次数
	String REST_NUMBER = "restNumber";

	/**
	 * 验证码已验证
	 */
	int SMS_VERIFIED = 1;

	//**==============================================客户服务记录================================**/
	Integer SYSTEM_FLAG = 1;

	Integer CUSTOM_FLAG = 2;


	//=====================================================微信推送跳转url=================================/
	String SERVICE_ORDER_SUCCESS_URL = "";

	String MEAL_CARD_ORDER_SUCCESS_URL = "";

	String HOUSEKEEPER_RECEIPT_SUCCESS = "";

	String SERVICE_FINISHED = "";

	String WAIT_FOR_RECEIPT = "";
}
