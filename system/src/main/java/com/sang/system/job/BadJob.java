package com.sang.system.job;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * job错误重试示例
 */
@Slf4j
@DisallowConcurrentExecution
// 保存jobData
@PersistJobDataAfterExecution
public class BadJob extends QuartzJobBean {

    public static final String EXECUTION_COUNT = "count";
    public static final String EXECUTION_DELAY = "ExecutionDelay";

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        try {
            if(null == jobDataMap.get(EXECUTION_COUNT)) {
                jobDataMap.put(EXECUTION_COUNT,1);
            } else {
                jobDataMap.put(EXECUTION_COUNT,jobDataMap.getInt(EXECUTION_COUNT)+1);
            }
            Thread.sleep(3000);

            log.info("demo print2");
        } catch (Exception e) {

            if (jobDataMap.getInt(EXECUTION_COUNT) < 5) {
                // 失败重试
                log.error("错误重试，睡眠{}ms",jobDataMap.getInt(EXECUTION_DELAY));
                // 延迟多久重试
                Thread.sleep(jobDataMap.getInt(EXECUTION_DELAY));
                JobExecutionException jobExecutionException = new JobExecutionException(e, true);
                // jobExecutionException.setUnscheduleAllTriggers(true); 解除所有相关触发器，这个job不会再次触发
                throw jobExecutionException;
            } else {
                log.error("超过最大重试次数");
            }


        }
    }
}
