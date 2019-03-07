package top.klw8.alita.validator;

import java.util.Map;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import top.klw8.alita.base.springctx.SpringApplicationContextUtil;
import top.klw8.alita.validator.cfg.ValidatorsConfig;

/**
 * @ClassName: EnableValidatorSelector
 * @Description: 将处理验证器的切面注入到spring中
 * @author klw
 * @date 2018年9月17日 下午4:41:28
 */
public class EnableValidatorSelector implements ImportSelector {
    
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
	Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableValidator.class.getName());
	Class<?> obj = (Class<?>) annotationAttributes.get("responseMsgGenerator");
	String[] otherImports = new String[] {SpringApplicationContextUtil.class.getName(), ValidatorAOP.class.getName(), obj.getName()};
	String[] validatorImports = ValidatorsConfig.getValidators();
	String[] allImports = new String[otherImports.length + validatorImports.length];
	System.arraycopy(otherImports, 0, allImports, 0, otherImports.length);
	System.arraycopy(validatorImports, 0, allImports, otherImports.length, validatorImports.length);
	return allImports;
    }
    
}
