package top.klw8.alita.validator.annotations.impl;

import java.lang.annotation.Annotation;

import top.klw8.alita.utils.ValidateUtil;
import top.klw8.alita.validator.IAnnotationsValidator;
import top.klw8.alita.validator.ValidatorException;
import top.klw8.alita.validator.ValidatorImpl;
import top.klw8.alita.validator.annotations.DoubleRange;
import top.klw8.alita.validator.annotations.FloatRange;
import top.klw8.alita.validator.annotations.GeoLatitude;
import top.klw8.alita.validator.annotations.GeoLongitude;
import top.klw8.alita.validator.annotations.IntegerRange;
import top.klw8.alita.validator.annotations.LongRange;

/**
 * @ClassName: NumberRangeImpl
 * @Description: 数字范围验证器实现
 * @author klw
 * @date 2019年1月26日 下午2:35:51
 */
@ValidatorImpl(validator = {DoubleRange.class, FloatRange.class, IntegerRange.class, LongRange.class, GeoLongitude.class, GeoLatitude.class})
public class NumberRangeImpl implements IAnnotationsValidator {

    @Override
    public void doValidator(Object object, Annotation annotation) throws Exception {
	if(object == null) {
	    // 不为null才验证
	    return;
	}
	if(annotation instanceof GeoLongitude) {
	    GeoLongitude geoLongitude = (GeoLongitude)annotation;
	    if(!ValidateUtil.checkLongitudeRange((Double)object)) {
		throw new ValidatorException(geoLongitude.responseStatusCode(), geoLongitude.validatFailMessage());
	    }
	} else if(annotation instanceof GeoLatitude) {
	    GeoLatitude geoLatitude = (GeoLatitude)annotation;
	    if(!ValidateUtil.checkLatitudeRange((Double)object)) {
		throw new ValidatorException(geoLatitude.responseStatusCode(), geoLatitude.validatFailMessage());
	    }
	} else if(annotation instanceof DoubleRange) {
	    DoubleRange doubleRange = (DoubleRange)annotation;
	    double value = ((Double)object).doubleValue();
	    if (doubleRange.min() > value || doubleRange.max() < value) {
		throw new ValidatorException(doubleRange.responseStatusCode(), doubleRange.validatFailMessage());
	    }
	} else if(annotation instanceof FloatRange) {
	    FloatRange floatRange = (FloatRange)annotation;
	    float value = ((Float)object).floatValue();
	    if (floatRange.min() > value || floatRange.max() < value) {
		throw new ValidatorException(floatRange.responseStatusCode(), floatRange.validatFailMessage());
	    }
	} else if(annotation instanceof IntegerRange) {
	    IntegerRange integerRange = (IntegerRange)annotation;
	    int value = ((Integer)object).intValue();
	    if (integerRange.min() > value || integerRange.max() < value) {
		throw new ValidatorException(integerRange.responseStatusCode(), integerRange.validatFailMessage());
	    }
	} else if(annotation instanceof LongRange) {
	    LongRange longRange = (LongRange)annotation;
	    long value = ((Long)object).longValue();
	    if (longRange.min() > value || longRange.max() < value) {
		throw new ValidatorException(longRange.responseStatusCode(), longRange.validatFailMessage());
	    }
	}
	
    }

}
