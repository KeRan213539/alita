package top.klw8.alita.validator.cfg;


/**
 * @ClassName: ValidatorsConfig
 * @Description: 验证器实现列表,配制验证器的实现,只有在这里配制的验证器实现才生效
 * @author klw
 * @date 2018年9月18日 下午3:49:28
 */
public class ValidatorsConfig {
    
    private static final String[] validators = new String[] {
	    "top.klw8.alita.validator.annotations.impl.RequiredImpl",
	    "top.klw8.alita.validator.annotations.impl.NotEmptyImpl",
	    "top.klw8.alita.validator.annotations.impl.MobilePhoneNumberImpl",
	    "top.klw8.alita.validator.annotations.impl.NumberRangeImpl",
	    "top.klw8.alita.validator.annotations.impl.PasswordImpl"
    };

    public static String[] getValidators() {
        return validators;
    }

}
