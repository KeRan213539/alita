package top.klw8.alita.orderService.test.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import top.klw8.alita.DemoServiceApplication;
import top.klw8.alita.entitys.demo.GeoPoint;
import top.klw8.alita.entitys.demo.MongoDBTest;
import top.klw8.alita.service.demo.mapper.demo.IMongoDBTestDao;


/**
 * @ClassName: TestDemo
 * @Description: 测试
 * @author klw
 * @date 2018年10月4日 下午7:04:19
 */
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= {DemoServiceApplication.class})
public class SpringDataMongodbTestDemo2 {

    @Autowired
    private IMongoDBTestDao testDao;
    
//    @Document(collection = "testPk") 
//    @Getter
//    @Setter
//    public class TestPkEntity implements Serializable, Cloneable{
//
//	private static final long serialVersionUID = 9091104928397337943L;
//	
//	@Id
//	private LongId id;
//	
//	private String name;
//	
//    }
    
//    @Test
//    public void testPk() {
//	
//	TestPkEntity testPkEntity = new TestPkEntity();
//	testPkEntity.setId(new LongId(11415477270351872L));
//	testPkEntity.setName("测试1");
//	
//	mongoTemplate.save(testPkEntity);
//    }
    
//    @Test
    public void testFindById() {
//	System.out.println(testDao.findById(11415477270351873L));
//	MongoDBTest t1 = new MongoDBTest();
//	t1.setId(PkGeneratorBySnowflake.INSTANCE.nextId());
//	t1.setName("测试数据");
//	t1.setSalary(new BigDecimal(50000.0));
//	t1.setLocation(new GeoPoint(102.694699,25.035302));
//	testDao.save(t1);
    }
    
    @Test
    public void testFind() {
	// 汇都1栋写字楼 102.70767,25.02523
//	Point point = new Point(102.70767,25.02523);
//	List<MongoDBTest> list =
//		mongoTemplate.find(new Query(
//	        Criteria.where("location").nearSphere(point).maxDistance(20/6378.137)) // maxDistance 距离单位是经纬度, 换算公里的方法: 公里数/6378.137
////			.addCriteria(new TextCriteria().matchingAny("报业")),  // geo和全文查询不能一起查
//			.addCriteria(Criteria.where("name").is("报业尚都"))
//			,
//			MongoDBTest.class);
//	System.out.println(list);
	
	// 下面的方法只能纯查位置,不能加其他条件
//	GeoResults<MongoDBTest> list2 =
//		mongoTemplate.geoNear(NearQuery.near(point).maxDistance(new Distance(1, Metrics.KILOMETERS)),
//			MongoDBTest.class);
//	System.out.println(list2);
	
//	List<MongoDBTest> list2 =
//		mongoTemplate.find(Query.query(new TextCriteria().matchingAny(AnalyzerUtil.strAnalyzerInnerSpace("翠湖"))).addCriteria(Criteria.where("salary").is(new BigDecimal(40000))),
//			MongoDBTest.class);
//	List<MongoDBTest> list2 =
//		testDao.getMongoTemplate().find(Query.query(Criteria.where("createDataTime").lte(LocalDateTimeUtil.formatToLDT("2019-01-15 10:20:30"))),
//			MongoDBTest.class);
//	System.out.println(list2);
    }
    
    
    
//    @Test
    public void testBatchSave() {
	List<MongoDBTest> list = new ArrayList<>();
	MongoDBTest t1 = new MongoDBTest();
	t1.setName("报业尚都");
	t1.setSalary(new BigDecimal(50000.0));
	t1.setLocation(new GeoPoint(102.694699,25.035302));
	t1.setCreateDataTime(LocalDateTime.now());
	list.add(t1);

	MongoDBTest t2 = new MongoDBTest();
	t2.setName("汇都二栋住宅");
	t2.setSalary(new BigDecimal(45000.0));
	t2.setLocation(new GeoPoint(102.707638,25.025478));
	t2.setCreateDataTime(LocalDateTime.parse("2019-01-01 18:20:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	list.add(t2);

	MongoDBTest t3 = new MongoDBTest();
	t3.setName("翠湖");
	t3.setSalary(new BigDecimal(40000.0));
	t3.setLocation(new GeoPoint(102.70399,25.048341));
	t3.setCreateDataTime(LocalDateTime.parse("2019-01-02 18:20:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	list.add(t3);
	
	MongoDBTest t4 = new MongoDBTest();
	t4.setName("西北部骑车客运站");
	t4.setSalary(new BigDecimal(40000.0));
	t4.setLocation(new GeoPoint(102.663993,25.092967));
	t4.setCreateDataTime(LocalDateTime.parse("2019-01-03 18:20:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	list.add(t4);
	
	MongoDBTest t5 = new MongoDBTest();
	t5.setName("报业尚都旁边");
	t5.setSalary(new BigDecimal(50000.0));
	t5.setLocation(new GeoPoint(102.694679,25.035312));
	t5.setCreateDataTime(LocalDateTime.parse("2019-01-05 18:20:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	list.add(t5);
	
	MongoDBTest t6 = new MongoDBTest();
	t6.setName("延安医院");
	t6.setSalary(new BigDecimal(50000.0));
	t6.setLocation(new GeoPoint(102.73079,25.042475));
	t6.setCreateDataTime(LocalDateTime.parse("2019-01-08 18:20:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	list.add(t6);
	
	MongoDBTest t7 = new MongoDBTest();
	t7.setName("长水国际机场");
	t7.setSalary(new BigDecimal(45000.0));
	t7.setLocation(new GeoPoint(102.927858,25.100317));
	t7.setCreateDataTime(LocalDateTime.parse("2019-01-10 18:20:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	list.add(t7);
	
	MongoDBTest t8 = new MongoDBTest();
	t8.setName("延安医院旁边");
	t8.setSalary(new BigDecimal(60000.0));
	t8.setLocation(new GeoPoint(102.73089,25.042485));
	t8.setCreateDataTime(LocalDateTime.parse("2019-01-12 18:20:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	list.add(t8);
	
	MongoDBTest t9 = new MongoDBTest();
	t9.setName("西北部骑车客运站旁边");
	t9.setSalary(new BigDecimal(41000.0));
	t9.setLocation(new GeoPoint(102.663903,25.092977));
	t9.setCreateDataTime(LocalDateTime.parse("2019-01-15 18:20:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	list.add(t9);
	
	testDao.batchSave(list);
    }
    
}
