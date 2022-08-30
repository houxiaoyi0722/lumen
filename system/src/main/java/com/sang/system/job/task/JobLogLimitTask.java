package com.sang.system.job.task;

import com.sang.system.service.job.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * job log 清理job，用于限制joblog表的记录数量，限制在100w条以内
 */
@Slf4j
// 禁用并行
@DisallowConcurrentExecution
public class JobLogLimitTask extends QuartzJobBean {

    @Resource
    private JobLogService jobLogService;


    @Override
    protected void executeInternal(JobExecutionContext context) {
        int num = jobLogService.limitJobLogSize();
        log.info("JobLogLimitTask： 删除"+num+"条log");
        context.setResult("删除"+num+"条log");
    }
}
