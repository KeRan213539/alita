package top.klw8.alita.mqConsumer.log.consumers.demo;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;



/**
 * @ClassName: StringTopicConsumer
 * @Description: string 消费者演示
 * @author klw
 * @date 2018-10-25 11:38:30
 */
@Service
@RocketMQMessageListener(topic = "string-topic", consumerGroup = "${spring.application.name}-string-topic")
public class StringTopicConsumer implements RocketMQListener<String> {

    
    @Override
    public void onMessage(String message) {
	System.out.println(message);
    }

}
