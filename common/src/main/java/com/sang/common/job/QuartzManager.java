package com.sang.common.job;


import com.sang.common.constants.ResultCodeEnum;
import com.sang.common.domain.job.JobVo;
import com.sang.common.domain.job.TriggerVo;
import com.sang.common.exception.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 计划任务管理
 */
@Service
@Log4j2
public class QuartzManager {

    @Resource
    private Scheduler scheduler;

    /**
     * 添加任务
     *
     * @param job
     * @throws SchedulerException
     */
    @SuppressWarnings("unchecked")
    public void addJob(JobVo job) {
        try {

            // 校验类是否存在以及是否继承job接口
            Object classObj = Class.forName(job.getBeanClass()).getDeclaredConstructor().newInstance();
            if(!(classObj instanceof Job))
                throw new BusinessException(ResultCodeEnum.CLASS_TYPE_NOT_EXTEND_JOB.getCode(),ResultCodeEnum.CLASS_TYPE_NOT_EXTEND_JOB.getMessage());

            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            JobDetail jobDetail = JobBuilder
                    .newJob((Class<? extends Job>) classObj.getClass())
                    .withIdentity(job.getJobName(), job.getJobGroup())// 任务名称和组构成任务key
                    .requestRecovery(job.isShouldRecover())
                    .withDescription(job.getDescription())
                    .storeDurably(job.isDurability())
                    .setJobData(job.getJobDataMap())
                    .build();
            // 定义调度触发规则
            // 使用cornTrigger规则
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())// 触发器key
                    .startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
                    .withDescription(job.getTriggerVo().getTriggerDescription())
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getTriggerVo().getCronExpression()))
                    .forJob(jobDetail)
                    .startNow().build();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    public List<JobVo> getAllJob() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        // 获取job列表
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<JobVo> jobList = new ArrayList<>();
        // 遍历job
        for (JobKey jobKey : jobKeys) {

            JobVo job = new JobVo();
            // 获取job明细
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setBeanClass(jobDetail.getJobClass().getName());
            job.setDescription(jobDetail.getDescription());
            job.setShouldRecover(jobDetail.requestsRecovery());
            // 获取触发器列表
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                TriggerVo triggerVo = new TriggerVo();
                // 获取触发器执行状态
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                triggerVo.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    triggerVo.setCronExpression(cronExpression);
                    triggerVo.setTriggerDescription(trigger.getDescription());
                }
                job.getTriggerVos().add(triggerVo);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public List<JobVo> getRunningJob() throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<JobVo> jobList = new ArrayList<JobVo>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            JobVo job = new JobVo();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.getTriggerVos().setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }

    /**
     * 暂停一个job
     *
     * @param task
     * @throws SchedulerException
     */
    public void pauseJob(JobVo task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param task
     * @throws SchedulerException
     */
    public void resumeJob(JobVo task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除一个job
     *
     * @param task
     * @throws SchedulerException
     */
    public void deleteJob(JobVo task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即执行job
     *
     * @param task
     * @throws SchedulerException
     */
    public void runJobNow(JobVo task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     *
     * @param job
     * @throws SchedulerException
     */
    public void updateJobCron(JobVo job) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getTriggerVos().getCronExpression());

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).withDescription(job.getTriggerVos().getTriggerDescription()).build();

        scheduler.rescheduleJob(triggerKey, trigger);
    }
}