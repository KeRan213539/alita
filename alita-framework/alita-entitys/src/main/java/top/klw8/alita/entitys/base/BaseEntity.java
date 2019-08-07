package top.klw8.alita.service.base.entitys;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * @ClassName: BaseEntity
 * @Description: 基础实体
 * @author klw
 * @date 2018年12月20日 下午2:25:41
 */
public class BaseEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * @author klw
	 * @Fields id : 主键ID
	 */
	@Id
	private ObjectId id;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getIdString() {
		return id.toString();
	}
}
