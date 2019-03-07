package top.klw8.alita.entitys.product;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.service.base.entitys.BaseEntity;

/**
 * @ClassName: ServiceType
 * @Description: 服务类型定义实体
 * @author klw
 * @date 2019年1月29日 下午4:33:32
 */
@Document(collection = "service_type")
@Getter
@Setter
public class ServiceType extends BaseEntity {

    private static final long serialVersionUID = 93367827380699183L;
    
    /**
     * @author klw
     * @Fields serviceTypeName : 服务类型名称
     */
    private String serviceTypeName;
    
}
