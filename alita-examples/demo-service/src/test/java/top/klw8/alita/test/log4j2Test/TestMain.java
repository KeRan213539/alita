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
package top.klw8.alita.test.log4j2Test;

import top.klw8.alita.test.log4j2Test.test1.Test1;
import top.klw8.alita.test.log4j2Test.test2.Test2;

/**
 * 2018年3月15日 下午2:27:39
 */
public class TestMain {
    
    public static void main(String[] args) {
	Test1 t1 = new Test1();
	Test2 t2 = new Test2();
	t1.doSomeing();
	t2.doSomeing();
    }
    
}
