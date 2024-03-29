/*
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 */

package com.sang.common.listener;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.job.entity.JobLog;
import com.sang.common.domain.job.repo.JobLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.jdbcjobstore.Constants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author hxy
 */
@Component
@Slf4j
public class GlobalJobListener implements JobListener {

    @Resource
    private JobLogRepository jobLogRepository;

    public String getName() {
        return "GlobalJobListener";
    }

    public void jobToBeExecuted(JobExecutionContext inContext) {


        try {
            JobDetail jobDetail = inContext.getJobDetail();

            JobLog jobLog = JobLog.builder()
                    .jobName(jobDetail.getKey().getName()).jobGroup(jobDetail.getKey().getGroup())
                    .beanClass(jobDetail.getJobClass().getName()).jobDataMap(jobDetail.getJobDataMap())
                    .status(Constants.STATE_EXECUTING).startTime(new Date())
                    .mayFireAgain(inContext.getTrigger().mayFireAgain())
                    .build();

            Trigger trigger = inContext.getTrigger();

            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                jobLog.setCronExpression(cronTrigger.getCronExpression());
            }

            jobLogRepository.insert(jobLog);

            JobDataMap jobDataMap = inContext.getJobDetail().getJobDataMap();
            jobDataMap.put(StringConst.JOB_LOG_ID,jobLog.getId());
        } catch (Exception e) {
            log.error("全局job监听器保存报错",e);
        }
    }

    public void jobExecutionVetoed(JobExecutionContext inContext) {
        try {
            jobLogRepository.updateJobStatus(inContext.getJobDetail().getJobDataMap(), inContext.getResult(),inContext.getTrigger().getEndTime(),inContext.getJobRunTime(), "VETOED");
            log.info("Job1Listener says: Job {} : {} Execution was vetoed.",inContext.getJobDetail().getKey().getGroup(), inContext.getJobDetail().getKey().getName());
        } catch (Exception e) {
            log.error("全局job监听器禁止执行处理报错",e);
        }
    }

    public void jobWasExecuted(JobExecutionContext inContext, JobExecutionException inException) {
        try {
            if (inException == null) {
                jobLogRepository.updateJobStatus(inContext.getJobDetail().getJobDataMap(), inContext.getResult(),new Date(),inContext.getJobRunTime(), Constants.STATE_COMPLETE);
            } else {
                jobLogRepository.updateJobStatus(inContext.getJobDetail().getJobDataMap(), ExceptionUtil.stacktraceToString(inException),new Date(),inContext.getJobRunTime(), Constants.STATE_ERROR);
            }

            log.info("Job1Listener says: Job {} : {} was executed." ,inContext.getJobDetail().getKey().getGroup(), inContext.getJobDetail().getKey().getName());
        } catch (Exception e) {
            log.error("全局job监听器结束后处理报错",e);
        }
    }

}
