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
package top.klw8.alita.starter.authorization.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.api.authority.IAlitaUserProvider;
import top.klw8.alita.service.utils.EntityUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author klw
 * @ClassName: AlitaUserDetailsService
 * @Description: org.springframework.security.core.userdetails.UserDetailsService 的实现,根据用户名查找用户, 提供给 spring-security 使用
 * @date 2018年11月12日 下午5:20:38
 */
@Slf4j
public class AlitaUserDetailsService implements UserDetailsService {

    private IAlitaUserProvider userService;

    public AlitaUserDetailsService(IAlitaUserProvider userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CompletableFuture<AlitaUserAccount> userFuture =  userService.findUserByName(username);
		AlitaUserAccount user = null;
		try {
			user = userFuture.get();
            if(user == null){
                user = userService.findUserByPhoneNum(username).get();
            }
		} catch (InterruptedException | ExecutionException e) {
			log.error("", e);
		}
		if (EntityUtil.isEntityHasId(user)) {
            return user;
        }
        throw new UsernameNotFoundException(username);
    }

}
