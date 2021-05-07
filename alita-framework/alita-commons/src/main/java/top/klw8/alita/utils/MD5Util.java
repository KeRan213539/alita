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
package top.klw8.alita.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * MD5工具
 * 2019-01-31 17:44:15
 */
public class MD5Util {
    /** 
     * Encrypt string using MD5 algorithm 
     */  
    public final static String encryptMD5(String source) {  
        if (source == null) {  
            source = "";  
        }  
        String result = "";  
        try {  
            result = encrypt(source, "MD5");  
        } catch (NoSuchAlgorithmException ex) {  
            // this should never happen  
            throw new RuntimeException(ex);  
        }  
        return result.toUpperCase();  
    }  
    /** 
     * Encrypt string using SHA algorithm 
     */  
    public final static String encryptSHA(String source) {  
        if (source == null) {  
            source = "";  
        }  
        String result = "";  
        try {  
            result = encrypt(source, "SHA");  
        } catch (NoSuchAlgorithmException ex) {  
            // this should never happen  
            throw new RuntimeException(ex);  
        }  
        return result;  
    }  
    /** 
     * Encrypt string 
     */  
    private final static String encrypt(String source, String algorithm)  
            throws NoSuchAlgorithmException {  
        byte[] resByteArray = encrypt(source.getBytes(), algorithm);  
        return toHexString(resByteArray);  
    }  
    /** 
     * Encrypt byte array. 
     */  
    private final static byte[] encrypt(byte[] source, String algorithm)  
            throws NoSuchAlgorithmException {  
        MessageDigest md = MessageDigest.getInstance(algorithm);  
        md.reset();  
        md.update(source);  
        return md.digest();  
    }  
    /** 
     * Get hex string from byte array 
     */  
    private final static String toHexString(byte[] res) {  
        StringBuffer sb = new StringBuffer(res.length << 1);  
        for (int i = 0; i < res.length; i++) {  
            String digit = Integer.toHexString(0xFF & res[i]);  
            if (digit.length() == 1) {  
                digit = '0' + digit;  
            }  
            sb.append(digit);  
        }  
        return sb.toString().toUpperCase();  
    }  
    
}
