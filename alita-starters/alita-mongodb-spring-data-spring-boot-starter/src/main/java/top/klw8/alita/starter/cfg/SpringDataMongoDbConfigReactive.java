package top.klw8.alita.starter.cfg;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.util.Assert;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

import top.klw8.alita.config.beans.mongodb.MongoConfigBean;

/**
 * @ClassName: SpringDataMongoDbConfigReactive
 * @Description: spring-data-mongodb Reactive 的配制
 * @author klw
 * @date 2018-12-21 16:33:12
 */
@Configuration
@EnableConfigurationProperties(MongoConfigBean.class)
@EnableReactiveMongoRepositories
public class SpringDataMongoDbConfigReactive extends AbstractReactiveMongoConfiguration {

    @Resource
    private MongoConfigBean mongoConfigBean;
    
    @Override
    @Bean
    public MongoClient reactiveMongoClient() {
	Assert.notNull(mongoConfigBean, "mongodb 配置不能为空");
	Assert.hasText(mongoConfigBean.getConnectUrl(), "mongodb 连接字符串不能为空");
	return MongoClients.create(new ConnectionString(mongoConfigBean.getConnectUrl()));
    }

    @Override
    protected String getDatabaseName() {
	Assert.notNull(mongoConfigBean, "mongodb 配置不能为空");
	Assert.hasText(mongoConfigBean.getDbName(), "mongodb 数据库名不能为空");
	return mongoConfigBean.getDbName();
    }
    
    @Override
    protected Collection<String> getMappingBasePackages() {
	Assert.notNull(mongoConfigBean, "mongodb 配置不能为空");
	List<String> mapPackages = mongoConfigBean.getMapPackages();
	Assert.notEmpty(mapPackages, "要扫描并映射的包路径不能为空");
	return mapPackages;
    }
    
    @Override
    @Bean
    public MappingMongoConverter mappingMongoConverter() throws Exception {
	// 一定要把 MappingMongoConverter 配制成 Bean,否则自定义转换器不生效!!!
	// 纠结了N个小时,就是差了这个 !!!
	MappingMongoConverter converter = super.mappingMongoConverter();
//	List<Converter<?, ?>> converters = new ArrayList<>();
//	converters.add(LongIdToIdConverter.INSTANCE);
//	converters.add(IdToLongIdConverter.INSTANCE);
	
//	converter.setCustomConversions(new MongoCustomConversions(converters));
//	converter.setCustomConversions(new MongoCustomConversions(Collections.emptyList()));
	// 去掉保存到数据中的 _class 字段
	converter.setTypeMapper(new DefaultMongoTypeMapper(null)); 
	return converter;
    }
    
    @Override
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() throws Exception {
	return (ReactiveMongoTemplate) super.reactiveMongoTemplate();
    }


}
