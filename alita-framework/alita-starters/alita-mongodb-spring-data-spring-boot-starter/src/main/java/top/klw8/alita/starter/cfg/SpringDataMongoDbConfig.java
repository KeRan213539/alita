//package top.klw8.alita.starter.cfg;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import com.mongodb.MongoClientURI;
//import com.mongodb.client.MongoClient;
//import com.mongodb.reactivestreams.client.internal.MongoClientImpl;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.config.MongoConfigurationSupport;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
//import org.springframework.data.mongodb.core.convert.DbRefResolver;
//import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
//import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
//import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
//import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
//import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.util.Assert;
//
//import top.klw8.alita.config.beans.mongodb.MongoConfigBean;
//
///**
// * @ClassName: SpringDataMongoDbConfig
// * spring-data-mongodb 非 Reactive 的配制
// * 2018-12-21 16:33:12
// */
//@Configuration
//@EnableConfigurationProperties(MongoConfigBean.class)
//@EnableMongoRepositories
//public class SpringDataMongoDbConfig extends MongoConfigurationSupport {
//
//    @Resource
//    private MongoConfigBean mongoConfigBean;
//
//    public MongoClient mongoClient() {
//        Assert.notNull(mongoConfigBean, "mongodb 配置不能为空");
//        Assert.hasText(mongoConfigBean.getConnectUrl(), "mongodb 连接字符串不能为空");
////        return new MongoClientImpl(new MongoClientURI(mongoConfigBean.getConnectUrl()));
//        // TODO
//        return null;
//    }
//
//    @Bean
//    public MongoDatabaseFactory mongoDbFactory() {
//        return new SimpleMongoClientDatabaseFactory(mongoClient(), getDatabaseName());
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        Assert.notNull(mongoConfigBean, "mongodb 配置不能为空");
//        Assert.hasText(mongoConfigBean.getDbName(), "mongodb 数据库名不能为空");
//        return mongoConfigBean.getDbName();
//    }
//
//    @Override
//    protected Collection<String> getMappingBasePackages() {
//        Assert.notNull(mongoConfigBean, "mongodb 配置不能为空");
//        List<String> mapPackages = mongoConfigBean.getMapPackages();
//        Assert.notEmpty(mapPackages, "要扫描并映射的包路径不能为空");
//        return mapPackages;
//    }
//
//    public MappingMongoConverter mappingMongoConverter() throws Exception {
//        // 一定要把 MappingMongoConverter 配制成 Bean,否则自定义转换器不生效!!!
//        // 纠结了N个小时,就是差了这个 !!!
//        //	DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
//        //	MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext());
//        //	List<Converter<?, ?>> converters = new ArrayList<>();
//        //	converters.add(LongIdToIdConverter.INSTANCE);
//        //	converters.add(IdToLongIdConverter.INSTANCE);
//
//        //	converter.setCustomConversions(new MongoCustomConversions(converters));
//        //	converter.setCustomConversions(new MongoCustomConversions(Collections.emptyList()));
//
//        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
//        MongoCustomConversions conversions = new MongoCustomConversions(Collections.emptyList());
//
//        MongoMappingContext mappingContext = new MongoMappingContext();
//        mappingContext.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
//        mappingContext.afterPropertiesSet();
//
//        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
//        converter.setCustomConversions(conversions);
//        converter.afterPropertiesSet();
//        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
//
//        return converter;
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//        // 去掉保存到数据中的 _class 字段
//        // MappingMongoConverter converter = mappingMongoConverter();
//        // converter.setTypeMapper(new DefaultMongoTypeMapper(null));
//        // return new MongoTemplate(mongoDbFactory(), converter);
//
//        return new MongoTemplate(mongoDbFactory(), mappingMongoConverter());
//    }
//
//}
