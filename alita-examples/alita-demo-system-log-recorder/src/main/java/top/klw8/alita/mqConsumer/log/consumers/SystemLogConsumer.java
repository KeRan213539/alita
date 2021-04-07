/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
