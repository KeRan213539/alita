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
package top.klw8.alita.starter.authorization.cfg.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: TokenConfigBean
 * @Description: token配制bean
 * @date 2020/8/17 11:20
 */
@Getter
@Setter
@ConfigurationProperties(prefix="alita.oauth2.token")
public class TokenConfigBean {

    private boolean storeInRedis = false;

    private TokenTimeoutConfigBean timeoutSeconds = new TokenTimeoutConfigBean();


}
