package top.klw8.alita.entitys.base;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName: BaseEntity
 * @Description: 基础实体
 * @author klw
 * @date 2018年12月20日 下午2:25:41
 */
@Getter
@Setter
public class BaseEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * @author klw
	 * @Fields id : 主键ID
	 */
	@TableId(type = IdType.UUID)
	private String id;

}
