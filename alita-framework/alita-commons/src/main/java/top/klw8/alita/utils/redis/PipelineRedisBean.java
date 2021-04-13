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
package top.klw8.alita.utils.redis;

/**
 * redis 批量插入的bean
 * 2018年6月11日 上午11:28:28
 */
public class PipelineRedisBean {
    
    public PipelineRedisBean(String key, Object value, Long seconds) {
	this.key = key;
	this.value = value;
	this.seconds = seconds;
    }

    /**
     * key : 缓存的key
     */
    private String key;
    
    /**
     * value : 要被缓存的对象
     */
    private Object value;
    
    /**
     * seconds : 缓存时长(秒),传null则永久缓存
     */
    private Long seconds;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getSeconds() {
        return seconds;
    }

    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }
    
}
