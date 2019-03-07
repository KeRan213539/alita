package top.klw8.alita.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import top.klw8.alita.base.springctx.SpringApplicationContextUtil;

/**
 * @ClassName: ValidatorAOP
 * @Description: 处理验证器的切面
 * @author klw
 * @date 2018年9月17日 下午4:46:03
 */
@Aspect
public class ValidatorAOP {
    
    private static Logger logger = LoggerFactory.getLogger(ValidatorAOP.class);
    
    /**
     * @author klw
     * @Fields validatorImplsMap : 验证器注解对应的实现的缓存
     */
    private volatile static Map<String, Class<? extends IAnnotationsValidator>> validatorImplsCacheMap = null;
    
    @Autowired
    private IResponseMsgGenerator respGenerator;

    @Pointcut("@annotation(top.klw8.alita.validator.UseValidator)")
    public void validatorAOPAdvise() {
    }

    @Around("validatorAOPAdvise()") // 也可以将上面 @Pointcut 的内容直接放这里
    public Object doAdvise(ProceedingJoinPoint pjp) {
	
	if(validatorImplsCacheMap == null) {
	    synchronized (this) {
		if(validatorImplsCacheMap == null) {
		    validatorImplsCacheMap = new ConcurrentHashMap<>();
		    Map<String, Object> validatorImplsMap = SpringApplicationContextUtil.getBeansWithAnnotation(ValidatorImpl.class);
		    if(MapUtils.isNotEmpty(validatorImplsMap)) {
			for(Entry<String, Object> entry : validatorImplsMap.entrySet()) {
			    IAnnotationsValidator validatorImpl = (IAnnotationsValidator) entry.getValue();
			    ValidatorImpl validatorImplAnnotation = validatorImpl.getClass().getAnnotation(ValidatorImpl.class);
			    for(Class<? extends Annotation> vali : validatorImplAnnotation.validator()) {
				Class<? extends IAnnotationsValidator> impl = validatorImplsCacheMap.get(vali.getName());
				if(impl != null) {
				    logger.warn("【警告】验证器【" + vali.getName() + "】有多个实现,【" + impl.getName() + "】被替换为【" + validatorImpl.getClass().getName() + "】");
				}
				validatorImplsCacheMap.put(vali.getName(), validatorImpl.getClass());
			    }
			}
		    }
		}
	    }
	}
	
	// 获取被拦截的方法的参数
	Object[] args = pjp.getArgs();
	// 遍历该方法的所有参数
	if (args != null && args.length > 0) {
	    for (Object arg : args) {
		if(arg == null) {
		    continue;
		}
		Class<?> argClassz = arg.getClass();
		List<Field> fieldList = getAllFields(null, argClassz); // 获取所有字段
		// 遍历所有字段,并找出有注解的
		for (Field field : fieldList) {
		    // 检查每个字段的注解,有注解的才处理
		    Annotation[] fieldAnns = field.getAnnotations();
		    if (fieldAnns != null && fieldAnns.length > 0) {
			// 遍历该字段的注解,找到验证器的注解
			for (Annotation fieldAnn : fieldAnns) {
			    try {
				// 检查该注解是否有@ThisIsValidator,有就说明是验证器
				if (fieldAnn.annotationType().getAnnotation(ThisIsValidator.class) != null) {
				    // 通过spring拿到验证器进行验证,先拿验证器的springBeanName
				    Class<?> validatorSpringBeanClass = (Class<?>) validatorImplsCacheMap
					    .get(fieldAnn.annotationType().getName());
				    // 名字有值,从spring容器中拿对应的验证器
				    IAnnotationsValidator annotationsValidator = (IAnnotationsValidator) SpringApplicationContextUtil
					    .getBean(validatorSpringBeanClass);
				    if (annotationsValidator != null) {
					// 验证器不为空,调用验证器
					field.setAccessible(true);
					try {
					    annotationsValidator.doValidator(field.get(arg),
						    fieldAnn);
					} catch (ValidatorException ex) {
					    String errMsg = null;
					    if (StringUtils.isBlank(ex.getErrorMsg())) {
						errMsg = "【该验证器注解没有设置错误消息】";
					    } else {
						errMsg = ex.getErrorMsg();
					    }
					    return respGenerator
						    .generatorResponse(ex.getStatusCode(), errMsg);
					} catch (Exception ex) {
					    logger.error(
						    "验证器【{}】里抛出了 ValidatorException 以外的异常,请验证器开发人员注意!!!",
						    ex, fieldAnn.annotationType());
					    return respGenerator.generatorResponse("500",
						    "服务器内部错误:====" + ex.getMessage());
					}
				    } else {
					logger.error(
						"验证器【{}】指定的验证器实现没有找到,请验证器开发人员检查是否在 top.klw8.alita.validator.cfg.ValidatorsConfig 中配制了该实现",
						fieldAnn.annotationType());
				    }
				}
			    } catch (SecurityException | IllegalArgumentException e) {
				logger.error("验证器处理切面出了点问题", e);
			    }

			}
		    }
		}
	    }
	}
	Object ret = null;
	try {
	    ret = pjp.proceed();
	} catch (Throwable e) {
	    throw new RuntimeException("AOP Point Cut ValidatorAOP Throw Exception :" + e.getMessage(), e);
	}
	return ret;
    }
    
    /**
     * @Title: getAllFields
     * @Description: 递归获取该类的所有属性包括父类的爷爷类的...祖宗类的
     * @param fieldList
     * @param classz
     * @return
     */
    private List<Field> getAllFields(List<Field> fieldList, Class<?> classz) {
	if(classz == null) {
	    return fieldList;
	}
	if(fieldList == null) {
	    fieldList = new ArrayList<Field>(Arrays.asList(classz.getDeclaredFields()));  // 获得该类的所有字段,但不包括父类的
	    /**
	     * 巨坑巨坑!!!
	     * Arrays.asList() 返回的是 Arrays的内部类ArrayList， 而不是java.util.ArrayList。
	     * Arrays的内部类ArrayList和java.util.ArrayList都是继承AbstractList，
	     * remove、add等方法AbstractList中是默认throw UnsupportedOperationException而且不作任何操作。
	     * java.util.ArrayList重写了这些方法而Arrays的内部类ArrayList没有重写，所以会抛出异常。
	     * 解决办法就是把 Arrays.asList() 返回的东西再丢到 new java.util.ArrayList() 里面,重新生成一个java.util.ArrayList
	     */
	} else {
	    CollectionUtils.addAll(fieldList, classz.getDeclaredFields()); // 获得该类的所有字段,但不包括父类的
	}
	return getAllFields(fieldList, classz.getSuperclass());
    }

}
