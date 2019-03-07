package top.klw8.alita.entitys.user.enums;

import lombok.Getter;

/**
 * @ClassName: NormalUserSourceEnum
 * @Description: 客户来源枚举
 * @author klw
 * @date 2019年1月11日 下午4:11:54
 */
@Getter
public enum NormalUserSourceEnum {

    TV("电视"),
    
    ROAD_SIGNS("路牌"),
    
    RADIO("广播"),
    
    ACTIVITY("活动"),
    
    OTHER("其他")
    ;
    
    private String describe;
    
    private NormalUserSourceEnum(String describe) {
	this.describe = describe;
    }
    
}
