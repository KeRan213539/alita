package top.klw8.alita.starter.web.common;

public class CallBackMessage {

	public static final String operationSuccess = "操作成功";
	public static final String operationError = "操作失败";
	
	public static final String sysError = "系统异常，请联系管理员";
	
	public static final String codeError = "验证码错误(可能已超时)";
	public static final String notMatchCode = "验证码错误(可能是验证码和手机号不匹配)";

	public static final String loginSuccess = "登陆成功";
	public static final String loginError = "网络异常登陆失败请重新登录";

	public static final String isExistTelepone = "手机号已注册";
	public static final String registerSuccess = "注册成功请牢记您的登陆密码"; 
	public static final String registerError = "网络异常注册失败请重新注册";

	public static final String notMatchTelepone = "您的输入的手机号码与当前账号不匹配";
	
	public static final String modifyPassWordSuccess = "修改密码成功请牢记您的密码"; 
	public static final String modifyPassWordError = "网络异常修改失败请重新修改";
	
	public static final String modifySuccess = "修改信息成功"; 
	public static final String modifyError = "网络异常修改失败请重新修改";
	
	
	public static final String deleteSuccess = "删除成功";
	public static final String deleteError = "删除失败";
	
	public static final String paramError = "参数错误";

	public static final String customerNotBound = "客户未绑定";

	public static final String passwordError = "原始密码错误";

	public static final String saveSuccess = "保存成功";
	public static final String saveError = "保存失败";

	public static final String querySuccess = "查询成功";
	public static final String queryError = "查询失败";

	public static final String msgTimeHint = "距上次发送还未超过一分钟，请稍后再试！";

	public static final String managerRegistered = "管家已注册";
	public static final String managerUnRegistered = "管家未注册";

	public static final String mobileRegistered = "手机号已注册";
	public static final String mobileUnRegistered = "手机号未注册";

	public static final String sendSuccess = "发送成功";
	public static final String sendError = "发送失败";

	public static final String verifySuccess = "验证成功";
	public static final String verifyError = "验证失败";

	public static final String resetSuccess = "重置成功";
	public static final String resetError = "重置失败";

	public static final String tokenError = "token失效";
	public static final String tokenNull = "token为空";

	public static final String verifyIdCardSuccess = "您填写的身份证号和姓名不一致请重新输入";
	public static final String verifyIdCardError = "实名认证成功";

	public static final String fileNotExist = "请选择需要上传的文件";

	public static final String fileEmpty = "文件数据为空";

	public static final String columExist = "请先删除子分类";

	public static final String dataExist = "该分类下还有材料";

	public static final String columChildrenExist = "该分类下仍有子分类,请在最底层分类添加";

	public static final String dataRepeat = "数据重复";

	public static final String couponStatusModifyError = "优惠券状态修改失败";
}
