package top.klw8.alita.service.base.entitys;

import java.io.Serializable;

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
	@org.springframework.data.annotation.Id
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
