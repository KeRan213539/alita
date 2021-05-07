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
package top.klw8.alita.mqConsumer.log.consumers.demo;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;



/**
 * string 消费者演示
 * 2018-10-25 11:38:30
 */
@Service
@RocketMQMessageListener(topic = "string-topic", consumerGroup = "${spring.application.name}-string-topic")
public class StringTopicConsumer implements RocketMQListener<String> {

    
    @Override
    public void onMessage(String message) {
	System.out.println(message);
    }

}
