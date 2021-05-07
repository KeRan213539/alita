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
package top.klw8.alita.job.executor.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.springframework.stereotype.Service;


/**
 * 分片广播任务
 *
 */
@Service
public class ShardingJobHandler extends IJobHandler {

    @Override
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        // 分片参数
        XxlJobHelper.log("分片参数：当前分片序号 = {0}, 总分片数 = {1}", XxlJobHelper.getShardIndex(), XxlJobHelper.getShardTotal());

        // 业务逻辑
        for (int i = 0; i < XxlJobHelper.getShardTotal(); i++) {
            if (i == XxlJobHelper.getShardIndex()) {
                XxlJobHelper.log("第 {0} 片, 命中分片开始处理", i);
            } else {
                XxlJobHelper.log("第 {0} 片, 忽略", i);
            }
        }
        XxlJobHelper.handleSuccess();
    }

}
