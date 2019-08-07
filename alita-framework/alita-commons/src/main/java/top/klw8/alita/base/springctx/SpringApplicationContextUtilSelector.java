package top.klw8.alita.base.springctx;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName: SpringApplicationContextUtilSelector
 * @Description: 将 SpringApplicationContextUtil 注入到 spring中
 * @author klw
 * @date 2018年9月17日 下午1:58:55
 */
public class SpringApplicationContextUtilSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
	return new String[] {SpringApplicationContextUtil.class.getName()};
    }

}
