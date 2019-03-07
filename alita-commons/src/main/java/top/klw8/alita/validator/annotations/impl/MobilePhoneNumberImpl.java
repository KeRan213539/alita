package top.klw8.alita.validator.annotations.impl;

import java.lang.annotation.Annotation;

import top.klw8.alita.utils.ValidateUtil;
import top.klw8.alita.validator.IAnnotationsValidator;
import top.klw8.alita.validator.ValidatorException;
import top.klw8.alita.validator.ValidatorImpl;
import top.klw8.alita.validator.annotations.MobilePhoneNumber;


/**
 * @ClassName: MobilePhoneNumberImpl
 * @Description: 验证手机号格式
 * @author klw
 * @date 2018-11-22 16:58:22
 */
@ValidatorImpl(validator = MobilePhoneNumber.class)
public class MobilePhoneNumberImpl implements IAnnotationsValidator {

    @Override
    public void doValidator(Object object, Annotation annotation) throws Exception {
	if (object != null) {
	    // 不为null才验证
	    MobilePhoneNumber mobilePhoneNumber = (MobilePhoneNumber) annotation;
	    if (!ValidateUtil.isMobileNO((String) object)) {
		throw new ValidatorException(mobilePhoneNumber.responseStatusCode(),
			mobilePhoneNumber.validatFailMessage());
	    }
	}
    }

}
