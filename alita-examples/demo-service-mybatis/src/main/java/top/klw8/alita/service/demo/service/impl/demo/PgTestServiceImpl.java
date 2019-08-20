package top.klw8.alita.service.demo.service.impl.demo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import top.klw8.alita.entitys.demo.mongo.PgTest;
import top.klw8.alita.service.api.mybatis.IPgTestService;
import top.klw8.alita.service.demo.mapper.demo.PgTestMapper;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: postgresql demo service 实现
 * @Description:
 * @date 2019/8/8 14:12
 */
@Slf4j
@Service(async = true)
public class PgTestServiceImpl extends ServiceImpl<PgTestMapper, PgTest> implements IPgTestService {
}
