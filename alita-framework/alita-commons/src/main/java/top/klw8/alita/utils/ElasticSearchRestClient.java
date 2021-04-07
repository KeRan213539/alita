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
//package top.klw8.alita.utils;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.http.HttpHost;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
//import org.apache.http.impl.nio.reactor.IOReactorConfig;
//import org.elasticsearch.action.DocWriteResponse;
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.client.Response;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
///**
// * @ClassName: ElasticSearchRestClientUtil
// * @Description: ElasticSearchRest 客户端工具类
// * @author klw
// * @date 2018年4月13日 上午11:37:19
// */
//public class ElasticSearchRestClient {
//
//    private static Logger logger = LoggerFactory.getLogger(ElasticSearchRestClient.class);
//
//    private volatile static Map<String, ElasticSearchRestClient> clients = new HashMap<>();
//
//    private static int clientsMax = 20;
//
//    public static ElasticSearchRestClient getInstance(String host, int port) throws Exception {
//	String clientKey = host + ":" + port;
//	ElasticSearchRestClient client = clients.get(clientKey);
//	if (client == null) {
//	    synchronized (ElasticSearchRestClient.class) {
//		if (client == null) {
//		    if (clients.size() >= clientsMax) {
//			throw new Exception("客户端数量已达上限【" + clientsMax + "】");
//		    }
//		    client = new ElasticSearchRestClient(host, port);
//		    clients.put(clientKey, client);
//		}
//	    }
//	}
//	return client;
//    }
//
//    private static RestHighLevelClient client;
//
//    private final String scheme = "http";
//
//    private final int maxRetryTimeoutMilli = 10001;
//
//    private final int httpClientSocketTimeou = 10002;
//
//    private ElasticSearchRestClient(String host, int port) {
//	RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(host, port, scheme));
//	clientBuilder.setMaxRetryTimeoutMillis(maxRetryTimeoutMilli);   // 超时时间默认30秒
//	clientBuilder.setFailureListener(new RestClient.FailureListener() {  // 失败监听器
//	    @Override
//	    public void onFailure(HttpHost host) {
//		RuntimeException ex = new RuntimeException(host + "访问超时");
//		logger.error("访问超时", ex);
//		throw ex;
//	    }
//	});
//
//	clientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {   //修改请求配制设置(request timeouts, authentication, or anything that the org.apache.http.client.config.RequestConfig.Builder allows to set)
//	    @Override
//	    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
//	        return requestConfigBuilder.setSocketTimeout(httpClientSocketTimeou)  // 超时时间默认30秒
//	        	.setConnectionRequestTimeout(10005);
//	    }
//	});
//
//	clientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//
//	    @Override
//	    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//		return httpClientBuilder.setDefaultIOReactorConfig(
//			IOReactorConfig.custom().setIoThreadCount(100)
//			.setConnectTimeout(10003)
//	                .setSoTimeout(10004)
//	                .build()
//	               );
//	    }
//	});
//	client = new RestHighLevelClient(clientBuilder);
//    }
//
//    /**
//     * @Title: isIndexExist
//     * @Description: 检查索引是否存在
//     * @param indexName 索引名称
//     * @return
//     */
//    public boolean isIndexExist(String indexName) {
//	try {
//	    Response resp = client.getLowLevelClient().performRequest("HEAD", indexName);
//	    return resp.getStatusLine().getStatusCode() == 200;  // 200 存在, 404 不存在
//	} catch (IOException e) {
//	    logger.error("检查索引是否存在时发生异常", e);
//	}
//	return false;
//    }
//
//    /**
//     * @Title: createIndex
//     * @Description: 创建索引
//     * @param indexName 索引名称
//     * @param typeName4Mapping 要做mapping的类型名称
//     * @param mappingJson mapping Json
//     * @param shards  分片数
//     * @param replicas  副本数
//     * @return
//     */
//    public boolean createIndex(String indexName, int shards, int replicas) {
//	CreateIndexRequest request = new CreateIndexRequest(indexName);
//	if(shards < 1){
//	    shards = 1;
//	}
//	if(replicas < 0) {
//	    replicas = 0;
//	}
//	request.settings(Settings.builder().put("index.number_of_shards", shards)
//		.put("index.number_of_replicas", replicas));
////	if(StringUtils.isNotBlank(typeName4Mapping) && StringUtils.isNotBlank(mappingJson)) {
////	    request.mapping(typeName4Mapping, mappingJson, XContentType.JSON);
////	}
//	try {
//	    CreateIndexResponse createIndexResponse = client.indices().create(request);
//	    return createIndexResponse.isAcknowledged();
////	    boolean acknowledged = createIndexResponse.isAcknowledged();   //是否所有节点都接受了该请求
////	    boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();  //在超时之前是否所有分片都启动了对应的副本数
//	} catch (IOException e) {
//	    logger.error("创建索引时发生异常", e);
//	}
//	return false;
//    }
//
//    /**
//     * @Title: putMapping
//     * @author klw
//     * @Description: 向索引里添加某类型的 mapping
//     * @param indexName
//     * @param typeName4Mapping
//     * @param mappingJson
//     * @return
//     */
//    public boolean putMapping(String indexName, String mappingJson) {
//	try {
//	    StringEntity stringEntity = new StringEntity(mappingJson, "UTF-8");
//	    stringEntity.setContentType("application/json");
//	    Response resp = client.getLowLevelClient().performRequest("PUT", indexName + "/_mapping/doc", new HashMap<>(), stringEntity);
//	    System.out.println(resp.toString());
//	} catch (IOException e) {
//	    return false;
//	}
//	return true;
//    }
//
//
//    /**
//     * @Title: addData2Index
//     * @Description: 添加数据到索引
//     * @param indexName 索引名称
//     * @param typeName  type名称
//     * @param dataJson  JSON格式的数据
//     * @return
//     */
//    public boolean addData2Index(String indexName, String dataJson) {
//	IndexRequest requestAddData = new IndexRequest(indexName, // 索引名称
//		"doc",  // 类型
//		UUIDUtil.getRandomUUIDString()); // ID
//	requestAddData.source(dataJson, XContentType.JSON); // 设置JSON格式数据
//	try {
//	    IndexResponse response = client.index(requestAddData);
//	    if (response.getResult() == DocWriteResponse.Result.CREATED || response.getResult() == DocWriteResponse.Result.UPDATED) {
//		return true;
//	    }
//	} catch (IOException e) {
//	    e.printStackTrace();
//	} catch (Exception e) {
//	    e.printStackTrace();
//	}
//	return false;
//    }
//
//    /**
//     * @Title: addData2Index4Log
//     * @Description: 添加日志数据
//     * @param logStr
//     * @return
//     */
//    public boolean addDataLogData(String indexName, String typeName, String logStr) {
//	String dataJson = "{\"log_type\": \"" + typeName + "\", \"content\":\"" + logStr + "\"}";
//	return addData2Index(indexName, dataJson);
//    }
//
//}
