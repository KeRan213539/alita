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
package top.klw8.test.jwt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Base64Utils;

/**
 * 解析 JWT 测试
 * 2018年11月2日 上午10:00:27
 */
public class DecodeJwtTest {
    public static void main(String[] args) {
//	String jwtStrPart1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
//	String jwtStrPart2 = "eyJleHAiOjE1NDExMTE0NDAsInVzZXJfbmFtZSI6InVzZXJfMSIsImF1dGhvcml0aWVzIjpbIlVTRVIiXSwianRpIjoiZmI4MTVhNzctNTU3Yy00NjEwLTgyMzgtMzA5ZDQwYjJlMTM3IiwiY2xpZW50X2lkIjoiaGFpZ2Vfd2VpeGluIiwic2NvcGUiOlsic2VsZWN0Il19";
//	System.out.println(new String(Base64Utils.decodeFromString(jwtStrPart1)));
//	System.out.println(new String(Base64Utils.decodeFromString(jwtStrPart2)));
//	System.out.println(System.currentTimeMillis());
	
	BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
	System.out.println(pwdEncoder.encode("123456"));
    }
}
