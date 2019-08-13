package top.klw8.alita.orderService.test.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import top.klw8.alita.DemoMybatisServiceApplication;
import top.klw8.alita.entitys.demo.PgTest;
import top.klw8.alita.service.api.mybatis.IPgTestService;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: PgTestJUnit
 * @Description: pgTest的JUnit测试
 * @date 2019/8/8 14:25
 */
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= {DemoMybatisServiceApplication.class})
public class PgTestJUnit {

    @Autowired
    private IPgTestService service;

    @Test
    public void test(){
        PgTest e = new PgTest();
        e.setName("测试1");
        service.save(e);
        System.out.println(service.count());
    }

}
