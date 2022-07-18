package com.sang.system.job;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * demo
 */
@Slf4j
// 禁用并行
@DisallowConcurrentExecution
public class DemoJob extends QuartzJobBean {

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        Thread.sleep(3000);
        log.info("demo print");
    }
}
