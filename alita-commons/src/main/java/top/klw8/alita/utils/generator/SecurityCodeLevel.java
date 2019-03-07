package top.klw8.alita.utils.generator;

/**
 * @ClassName: SecurityCodeLevel
 * @Description: 验证码难度级别，Simple只包含数字，Medium包含数字和小写英文，Hard包含数字和大小写英文
 * @author klw
 * @date 2016年11月4日 下午3:34:24
 */
public enum SecurityCodeLevel {
	Simple, //只包含数字
	Medium, //包含数字和小写英文
	Hard  //包含数字和大小写英文
};
