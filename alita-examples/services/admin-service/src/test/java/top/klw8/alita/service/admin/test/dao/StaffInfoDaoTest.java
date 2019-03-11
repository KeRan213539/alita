package top.klw8.alita.service.admin.test.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Mono;
import top.klw8.alita.AdminServiceApplication;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.entitys.user.StaffInfo;
import top.klw8.alita.service.admin.dao.IAlitaUserDao;
import top.klw8.alita.service.admin.dao.user.IStaffInfoDao;
import top.klw8.alita.utils.generator.PkGeneratorBySnowflake;

/**
 * @ClassName: StaffInfoDaoTest
 * @Description: 测试  StaffInfoDao
 * @author klw
 * @date 2019年1月30日 下午2:11:19
 */
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= {AdminServiceApplication.class})
public class StaffInfoDaoTest {

    @Autowired
    private IAlitaUserDao accountDao;
    
    @Autowired
    private IStaffInfoDao staffDao;
    
    @Test
    public void testAddData() {
	
//	Mono<AlitaUserAccount> accountMono = accountDao.findById(2175L);
//	accountMono.subscribe(account -> {
//	    StaffInfo staff = new StaffInfo();
//	    staff.setId(PkGeneratorBySnowflake.INSTANCE.nextId());
//	    staff.setAccountInfo(account);
//	    staff.setAge(28);
//	    staff.setRealName("测试员100号");
//	    staff.setPhoneNumber("15808888888");
//	    staffDao.save(staff);
//	});
	
    }
    
    @Test
    public void testFindByAccountId() {
//	AlitaUserAccount account = new AlitaUserAccount();
//	StaffInfo query = new StaffInfo();
//	account.setId(2175L);
//	query.setAccountInfo(account);
//	Mono<List<StaffInfo>> listMono = staffDao.findByEntityWithRefQuery(query, null);
//	listMono.subscribe(list -> {
//	    System.out.println(list.size());
//	});
    }
    
}
