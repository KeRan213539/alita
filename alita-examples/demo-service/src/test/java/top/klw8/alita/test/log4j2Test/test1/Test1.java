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
package top.klw8.alita.test.log4j2Test.test1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: Test1
 * @Description: 
 * @author klw
 * @date 2018年3月15日 下午2:11:37
 */
public class Test1 {

    private static Logger logger = LoggerFactory.getLogger(Test1.class);
    
    public void doSomeing() {
//	for(int i = 0; i < 100; i++) {
	    logger.error("测试1写了一个错误日志====rrr");
//	}
    }

}
