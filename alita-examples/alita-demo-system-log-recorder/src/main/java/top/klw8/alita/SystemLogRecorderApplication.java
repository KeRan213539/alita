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
package top.klw8.alita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//import top.klw8.alita.utils.ElasticSearchRestClient;



/**
 * 系统日志纪录-启动程序
 * 2018年4月16日 上午10:44:40
 */
@SpringBootApplication
public class SystemLogRecorderApplication {

    public static void main(String[] args) {
	
	ConfigurableApplicationContext ctx = SpringApplication.run(SystemLogRecorderApplication.class, args);
	
	String esHost = ctx.getEnvironment().getProperty("spring.elasticsearch.host");
	int esPort = Integer.valueOf(ctx.getEnvironment().getProperty("spring.elasticsearch.port"));
	
	String logMappingJson = "  {" +    //日志统一mapping,所有日志都用这个mapping
		    "      \"properties\": {" +
		    "        \"log_type\":{" +
		    "          \"type\": \"keyword\"" +
		    "	     }," +
		    "        \"content\": {" +
		    "          	\"type\": \"text\"," +
		    "		\"analyzer\": \"ik_max_word\"," +
		    "		\"search_analyzer\": \"ik_max_word\"" +
		    "        }" +
		    "      }" +
		    "  }";
	
	
	try {
//	    ElasticSearchRestClient esClient = ElasticSearchRestClient.getInstance(esHost, esPort);
//	    if(!esClient.isIndexExist("alita-new-log")) {   // 检查索引是否存在--省兜兜日志
//	        esClient.createIndex("alita-new-log", 5, 0);   //TODO: 单节点的情况下,副本数要设为0(最后一个参数)
//	        esClient.putMapping("alita-new-log", logMappingJson);
//	    }
//	    if (!esClient.isIndexExist("pay-center-log")) { // 检查索引是否存在--支付中心日志
//		esClient.createIndex("pay-center-log", 5, 0);  //TODO: 单节点的情况下,副本数要设为0(最后一个参数)
//		esClient.putMapping("pay-center-log", logMappingJson);
//	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
        
	
//	ElasticSearchRestClientUtil.getInstance().addDataLogData("sdd-log", "test-log", StringEscapeUtils.escapeJson("隧道发生大风嗯我日"));
//	ElasticSearchRestClientUtil.getInstance().addDataLogData("sdd-log", "test-log", StringEscapeUtils.escapeJson("11111"));
//	ElasticSearchRestClientUtil.getInstance().addDataLogData("sdd-log", "normal-log", StringEscapeUtils.escapeJson("22222"));
    }
}

