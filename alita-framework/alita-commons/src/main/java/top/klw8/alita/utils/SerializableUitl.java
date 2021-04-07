/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: SerializableUitl
 * @Description: 序列化,反序列化工具类
 * @author klw
 * @date 2016年6月6日 下午1:04:02
 */
@Slf4j(topic = "serializableUitl")
public class SerializableUitl {

    public static byte[] ObjectToByte(java.lang.Object obj) {
	byte[] bytes = null;
	try {
	    // object to bytearray
	    ByteArrayOutputStream bo = new ByteArrayOutputStream();
	    ObjectOutputStream oo = new ObjectOutputStream(bo);
	    oo.writeObject(obj);

	    bytes = bo.toByteArray();
	    bo.close();
	    oo.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if (log.isDebugEnabled()) {
	    int byteLength = bytes.length;
	    ObjectMapper objectMapper = new ObjectMapper();
	    String objJson = null;
	    try {
		objJson = objectMapper.writeValueAsString(obj);
		
	    } catch (JsonProcessingException e) {
	    }
	    String logStr = objJson + "      序列化后的数据大小: " + bytes.length + "B";
	    
	    // 如果数据大于100KB,则输出到一个单独的日志文件中,调试用
	    if (byteLength > (100 * 1024)) {
		log.debug(logStr);
	    } else {
		//logger.debug(logStr);
	    }
	}
	return bytes;
    }

    public static Object ByteToObject(byte[] bytes) {
	Object obj = null;
	try {
	    // bytearray to object
	    ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
	    ObjectInputStream oi = new ObjectInputStream(bi);
	    obj = oi.readObject();
	    bi.close();
	    oi.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return obj;
    }

}
