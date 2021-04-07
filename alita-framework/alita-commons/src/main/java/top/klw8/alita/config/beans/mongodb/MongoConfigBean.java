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
package top.klw8.alita.config.beans.mongodb;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName: MongoConfigBean
 * @Description: MongoDB 配制 Bean
 * @author klw
 * @date 2018年9月13日 上午10:42:19
 */
@ConfigurationProperties(prefix="mongodb")
public class MongoConfigBean {

    
    /**
     * 要扫描并映射的包路径
     */
    private List<String> mapPackages;
    
    /**
     * @Fields connectUrl : 连接字符串
     * 带登录的连接: mongodb://user1:pwd1@host1/?authSource=db1&ssl=true
     * 连接复制集群的连接  mongodb://host1:27017,host2:27017
     */
    private String connectUrl;
    
    /**
     * @author klw
     * @Fields dbName : 数据库名
     */
    private String dbName;
    
    /**
     * @Fields createIndexes : 是否创建索引
     */
    private boolean createIndexes = false;
    


    public String getConnectUrl() {
        return connectUrl;
    }

    public void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public List<String> getMapPackages() {
        return mapPackages;
    }

    public void setMapPackages(List<String> mapPackages) {
        this.mapPackages = mapPackages;
    }

    public boolean isCreateIndexes() {
        return createIndexes;
    }

    public void setCreateIndexes(boolean createIndexes) {
        this.createIndexes = createIndexes;
    }
    
    
}
