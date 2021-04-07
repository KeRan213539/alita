package top.klw8.alita.job.executor.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import org.springframework.stereotype.Service;


/**
 * 分片广播任务
 *
 * @author xuxueli 2017-07-25 20:56:50
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
