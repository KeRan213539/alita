package top.klw8.alita.config.redis;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.base.springctx.SpringApplicationContextUtil;
import top.klw8.alita.utils.BindConfig2BeanUtil;


/**
 * @ClassName: RedisConfig
 * @Description: Redis 配制
 * @author klw
 * @date 2018年10月30日 下午5:31:36
 */
@Slf4j
public class RedisRegister implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	RedisConfigBean configBean = new RedisConfigBean();
	BindConfig2BeanUtil.INSTANCE.bind(applicationContext, configBean);
	if(!configBean.isEnabled()) {
	    return;
	}
	Assert.hasText(configBean.getDefaultHost(), "redis 服务器IP不能为空");
	Assert.notNull(configBean.getDefaultPort(), "redis 服务器端口不能为空");
	RedisStandaloneConfiguration defaultRedisStandaloneConfiguration = new RedisStandaloneConfiguration(configBean.getDefaultHost(), configBean.getDefaultPort());
	if(StringUtils.isNotBlank(configBean.getDefaultPass())) {
	    defaultRedisStandaloneConfiguration.setPassword(RedisPassword.of(configBean.getDefaultPass()));
	}
	JedisConnectionFactory defaultJedisConnectionFactory = new JedisConnectionFactory(defaultRedisStandaloneConfiguration, JedisClientConfiguration.builder().usePooling().build());
	
	RedisSerializer<String> stringSerializer = new StringRedisSerializer();
	RedisSerializer<Object> jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
	RedisTemplate<String, Object> defaultRedisTemplate = new RedisTemplate<>();
	defaultRedisTemplate.setConnectionFactory(defaultJedisConnectionFactory);
	defaultRedisTemplate.afterPropertiesSet();
	defaultRedisTemplate.setKeySerializer(stringSerializer);
	defaultRedisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
	defaultRedisTemplate.setHashKeySerializer(stringSerializer);
	defaultRedisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
	
	ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
	DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
	defaultListableBeanFactory.registerSingleton("redisDefaultCacheTemplate", defaultRedisTemplate);
	
	here:
	if(configBean.getExtendHosts() != null && configBean.getExtendHosts().size() > 0
		&& configBean.getExtendPorts() != null && configBean.getExtendPorts().size() > 0
		&& configBean.getExtendBeanIds() != null && configBean.getExtendBeanIds().size() > 0) {
	    int checkSize = configBean.getExtendPorts().size();
	    if(configBean.getExtendPorts().size() != checkSize || configBean.getExtendBeanIds().size() != checkSize) {
		log.warn("【警告】redis.extend 中的配制不完整,extendHosts,extendPorts,extendPasss的数量要一致!extend 配制失败,只有 default!");
		break here;
	    }
	    for(int i = 0; i < configBean.getExtendPorts().size(); i++) {
		String host = configBean.getExtendHosts().get(i);
		Integer port = configBean.getExtendPorts().get(i);
		String beanId = configBean.getExtendBeanIds().get(i);
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
		if(configBean.getExtendPasss() != null && StringUtils.isNotBlank(configBean.getExtendPasss().get(i))) {
		    redisStandaloneConfiguration.setPassword(RedisPassword.of(configBean.getExtendPasss().get(i)));
		}
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		redisTemplate.afterPropertiesSet();
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
		defaultListableBeanFactory.registerSingleton(beanId, redisTemplate);
	    }
	    
	}
	
    }
    
}
