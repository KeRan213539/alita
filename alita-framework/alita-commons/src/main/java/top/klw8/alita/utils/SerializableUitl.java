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
