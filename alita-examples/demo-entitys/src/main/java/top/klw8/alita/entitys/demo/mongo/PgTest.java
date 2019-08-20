package top.klw8.alita.entitys.demo.mongo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: PgTest
 * @Description: postgresql demo实体
 * @date 2019/8/8 14:05
 */
@Getter
@Setter
@ToString
@TableName("pg_test")
public class PgTest implements java.io.Serializable {

    @TableId(type = IdType.UUID)
    private String id;

    private String name;

}
