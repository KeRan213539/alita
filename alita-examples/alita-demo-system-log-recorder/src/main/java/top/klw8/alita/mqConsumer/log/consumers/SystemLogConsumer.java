//package top.klw8.alita.mqConsumer.log.consumers;
//
//import org.apache.commons.text.StringEscapeUtils;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import top.klw8.alita.utils.ElasticSearchRestClient;
//
//
///**
// * @author klw
// * @ClassName: SystemLogConsumer
// * @Description: 系统日志消费者
// * @date 2018年9月11日 20:30:39
// */
//@Service
//@RocketMQMessageListener(topic = "alitaLog-new", consumerGroup = "${spring.application.name}-alitaLog-new")
//public class SystemLogConsumer implements RocketMQListener<MessageExt> {
//
//    @Value("${spring.elasticsearch.host}")
//    private String host;
//
//    @Value("${spring.elasticsearch.port}")
//    private int port;
//
//    @Override
//    public void onMessage(MessageExt message) {
////	System.out.println(message.getTags());
//		boolean result;
//        try {
//            result = ElasticSearchRestClient.getInstance(host, port).addDataLogData("alita-new-log", message.getTags(), StringEscapeUtils.escapeJson(new String(message.getBody())));
//        } catch (Exception e) {
//        	result = false;
//            e.printStackTrace();
//        }
//        if (!result) {
//            // System.out.println(arg0);
//            throw new RuntimeException("日志纪录失败,将重新消费");
//        }
//    }
//
//}
