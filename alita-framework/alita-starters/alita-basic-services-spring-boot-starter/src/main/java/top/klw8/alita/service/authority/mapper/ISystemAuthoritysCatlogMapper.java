package top.klw8.alita.service.authority.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ISystemAuthoritysCatlogMapper
 * @Description: 权限的目录DAO
 * @author klw
 * @date 2018年11月28日 下午3:40:05
 */
public interface ISystemAuthoritysCatlogMapper extends BaseMapper<SystemAuthoritysCatlog> {

}
