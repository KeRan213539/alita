package top.klw8.alita.utils;

import java.util.UUID;

public class UUIDUtil {
    /**
     * 获取UUID字符串
     * @return
     */
    public static String getRandomUUIDString(){
    	return UUID.randomUUID().toString().replace("-", "");
    }
    
    public static void main(String[] args) {
	for(int i = 0; i <5; i++) {
	    System.out.println(getRandomUUIDString());
	}
    }
    
} 
