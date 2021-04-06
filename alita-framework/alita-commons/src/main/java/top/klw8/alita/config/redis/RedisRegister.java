package top.klw8.alita.config.redis;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
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
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import top.klw8.alita.base.springctx.SpringApplicationContextUtil;
import top.klw8.alita.utils.BindConfig2BeanUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author klw
 * @ClassName: RedisConfig
 * @Description: Redis 配制
 * @date 2018年10月30日 下午5:31:36
 */
@Slf4j
public class RedisRegister implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisConfigBean configBean = new RedisConfigBean();
        BindConfig2BeanUtil.INSTANCE.bind(applicationContext, configBean, "redisConfigBean");
        if (!configBean.isEnabled()) {
            return;
        }
        Assert.hasText(configBean.getDefaultHost(), "redis 服务器IP不能为空");
        Assert.notNull(configBean.getDefaultPort(), "redis 服务器端口不能为空");
        JedisConnectionFactory defaultJedisConnectionFactory;
        if (configBean.getDefaultIsCluster()){
            List<String> tempList = new ArrayList<>(1);
            tempList.add(configBean.getDefaultHost() + ":" + configBean.getDefaultPort());
            RedisClusterConfiguration defaultRedisStandaloneConfiguration = new RedisClusterConfiguration(tempList);
            if (StringUtils.isNotBlank(configBean.getDefaultPass())) {
                defaultRedisStandaloneConfiguration.setPassword(RedisPassword.of(configBean.getDefaultPass()));
            }
            defaultJedisConnectionFactory = new JedisConnectionFactory(defaultRedisStandaloneConfiguration, JedisClientConfiguration.builder().usePooling().build());
        } else {
            RedisStandaloneConfiguration defaultRedisStandaloneConfiguration = new RedisStandaloneConfiguration(configBean.getDefaultHost(), configBean.getDefaultPort());
            if (StringUtils.isNotBlank(configBean.getDefaultPass())) {
                defaultRedisStandaloneConfiguration.setPassword(RedisPassword.of(configBean.getDefaultPass()));
            }
            defaultJedisConnectionFactory = new JedisConnectionFactory(defaultRedisStandaloneConfiguration, JedisClientConfiguration.builder().usePooling().build());
        }

        defaultJedisConnectionFactory.afterPropertiesSet();

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
        if (!CollectionUtils.isEmpty(configBean.getExtendHosts()) &&
                !CollectionUtils.isEmpty(configBean.getExtendPorts()) &&
                !CollectionUtils.isEmpty(configBean.getExtendBeanIds())) {
            int checkSize = configBean.getExtendPorts().size();
            if (configBean.getExtendPorts().size() != checkSize || configBean.getExtendBeanIds().size() != checkSize) {
                log.warn("【警告】redis.extend 中的配制不完整,extendHosts,extendBeanIds,extendPasss的数量要一致!extend 配制失败,只有 default!");
                break here;
            }
            for (int i = 0; i < configBean.getExtendPorts().size(); i++) {
                String host = configBean.getExtendHosts().get(i);
                Integer port = configBean.getExtendPorts().get(i);
                String beanId = configBean.getExtendBeanIds().get(i);

                JedisConnectionFactory jedisConnectionFactory;
                if (!CollectionUtils.isEmpty(configBean.getExtendIsCluster()) && null != configBean.getExtendIsCluster().get(i) && configBean.getExtendIsCluster().get(i).booleanValue()){
                    List<String> tempList = new ArrayList<>(1);
                    tempList.add(configBean.getDefaultHost() + ":" + configBean.getDefaultPort());
                    RedisClusterConfiguration redisStandaloneConfiguration = new RedisClusterConfiguration(tempList);
                    if (!CollectionUtils.isEmpty(configBean.getExtendPasss()) && StringUtils.isNotBlank(configBean.getExtendPasss().get(i))) {
                        redisStandaloneConfiguration.setPassword(RedisPassword.of(configBean.getDefaultPass()));
                    }
                    jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, JedisClientConfiguration.builder().usePooling().build());
                } else {
                    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
                    if (!CollectionUtils.isEmpty(configBean.getExtendPasss()) && StringUtils.isNotBlank(configBean.getExtendPasss().get(i))) {
                        redisStandaloneConfiguration.setPassword(RedisPassword.of(configBean.getDefaultPass()));
                    }
                    jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, JedisClientConfiguration.builder().usePooling().build());
                }

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
