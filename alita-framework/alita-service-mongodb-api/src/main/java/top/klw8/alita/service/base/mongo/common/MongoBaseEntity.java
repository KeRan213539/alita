package top.klw8.alita.service.base.mongo.common;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: MongoBaseEntity
 * @Description: mongodb base entity
 * @date 2019/8/10 14:25
 */
@Getter
@Setter
public class MongoBaseEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    private ObjectId id;

    public String getIdString(){
        return id.toString();
    }

}
