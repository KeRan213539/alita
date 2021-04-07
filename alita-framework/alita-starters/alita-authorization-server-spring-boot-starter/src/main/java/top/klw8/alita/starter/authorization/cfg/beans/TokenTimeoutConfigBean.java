package top.klw8.alita.starter.authorization.cfg.beans;

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
import lombok.Getter;
import lombok.Setter;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: TokenTimeoutConfigBean
 * @Description: token 超时配制bean
 * @date 2020/8/17 11:20
 */
@Getter
@Setter
public class TokenTimeoutConfigBean {

    /**
     * @author klw(213539@qq.com)
     * @Description: access token 超时时间(秒) 默认12小时
     */
    private int access = 43200;

    /**
     * @author klw(213539@qq.com)
     * @Description: refresh token 超时时间(秒) 默认30天
     */
    private int refresh = 2592000;

}
