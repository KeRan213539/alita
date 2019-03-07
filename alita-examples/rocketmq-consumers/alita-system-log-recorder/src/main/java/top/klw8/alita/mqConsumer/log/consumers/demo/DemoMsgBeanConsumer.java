package top.klw8.alita.mqConsumer.log.consumers.demo;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import top.klw8.alita.mq.messages.DemoMsgBean;



/**
 * @ClassName: StringTopicConsumer
 * @Description: DemoMsgBean 消费者演示
 * @author klw
 * @date 2018-10-25 11:38:30
 */
@Service
@RocketMQMessageListener(topic = "bean-topic", consumerGroup = "${spring.application.name}-bean-topic")
public class DemoMsgBeanConsumer implements RocketMQListener<DemoMsgBean> {

    
    @Override
    public void onMessage(DemoMsgBean message) {
	System.out.println(message);
    }

}
