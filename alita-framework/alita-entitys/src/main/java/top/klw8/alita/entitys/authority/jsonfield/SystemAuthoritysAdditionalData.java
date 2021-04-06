package top.klw8.alita.entitys.authority.jsonfield;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SystemAuthoritysAdditionalData
 * @Description: 权限扩展数据Bean
 * @date 2020/5/13 9:44
 */
@Getter
@Setter
public class SystemAuthoritysAdditionalData implements java.io.Serializable {

    public enum Type{
        /**
         * @author klw(213539@qq.com)
         * @Description: 数据权限动态源
         */
        DATA_SECURED_SOURCE_DYNAMIC
        ;
    }

    private Type type;

    private Map<String, Object> data;

    public void addData(String key, Object value){
        if(data == null){
            synchronized (this){
                if(data == null){
                    data = new HashMap<>(16);
                }
            }
        }
        data.put(key, value);
    }

}
