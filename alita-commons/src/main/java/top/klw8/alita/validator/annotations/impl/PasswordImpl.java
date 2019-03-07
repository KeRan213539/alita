package top.klw8.alita.validator.annotations.impl;

import java.lang.annotation.Annotation;

import top.klw8.alita.validator.IAnnotationsValidator;
import top.klw8.alita.validator.ValidatorException;
import top.klw8.alita.validator.ValidatorImpl;
import top.klw8.alita.validator.annotations.Password;

/**
 * @ClassName: PasswordImpl
 * @Description: 用户密码验证器实现
 * @author klw
 * @date 2019年1月30日 下午6:18:13
 */
@ValidatorImpl(validator = {Password.class})
public class PasswordImpl implements IAnnotationsValidator {

    @Override
    public void doValidator(Object object, Annotation annotation) throws Exception {
	if(object == null) {
	    // 不为null才验证
	    return;
	}
	Password pwdan = (Password)annotation;
	String pwd = (String)object;
	int pwdLength = pwd.length();
	if (pwdan.minLength() > pwdLength || pwdan.maxLength() < pwdLength) {
	    throw new ValidatorException(pwdan.responseStatusCode(), "密码长度必须大于" + pwdan.minLength() + "小于" + pwdan.maxLength());
	}
    }
    
}
