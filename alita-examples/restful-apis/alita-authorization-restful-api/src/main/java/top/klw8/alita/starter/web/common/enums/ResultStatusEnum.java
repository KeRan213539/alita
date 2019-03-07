package top.klw8.alita.starter.web.common.enums;

/**
 * @ClassName: ResultStatusEnum
 * @Description: JsonResultStatus 枚举
 * @author klw
 * @date 2018年12月7日 下午1:53:00
 */
public enum ResultStatusEnum {

    FAILED(1),
    
    SUCCESS(0)
    
    ;
    private int value;
    
    private ResultStatusEnum(int value) {
	this.value = value;
    }
    
    public int intValue() {
	return value;
    }
    
}
