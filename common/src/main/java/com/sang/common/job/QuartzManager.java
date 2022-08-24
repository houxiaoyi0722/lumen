package com.sang.common.job;


import cn.hutool.core.util.StrUtil;
import com.sang.common.constants.ResultCodeEnum;
import com.sang.common.domain.job.JobVo;
import com.sang.common.domain.job.TriggerVo;
import com.sang.common.exception.BusinessException;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.quartz.*;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @SneakyThrows
    public void saveJob(JobVo job) {
        // 校验类是否存在以及是否继承job接口
        Object classObj = Class.forName(job.getBeanClass()).getDeclaredConstructor().newInstance();
        if(!(classObj instanceof Job))
            throw new BusinessException(ResultCodeEnum.CLASS_TYPE_NOT_EXTEND_JOB.getCode(),StrUtil.format(ResultCodeEnum.CLASS_TYPE_NOT_EXTEND_JOB.getMessage(),job.getBeanClass()));

        // 创建jobDetail实例，绑定Job实现类
        // 指明job的名称，所在组的名称，以及绑定job类
        JobDetail jobDetail = JobBuilder
                .newJob((Class<? extends Job>) classObj.getClass())
                .withIdentity(job.getJobName(), job.getJobGroup())// 任务名称和组构成任务key
                .requestRecovery(job.isShouldRecover())
                .withDescription(job.getDescription())
                .storeDurably(true) // 持久化job 在无关联触发器时保留
                .setJobData(job.getJobDataMap())
                .build();
        scheduler.addJob(jobDetail,true);
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
     * 添加触发器
     *
     * @param job
     * @throws SchedulerException
     */
    public void addTriggersForJob(JobVo job) throws SchedulerException {
        // 添加所有触发器
        for (TriggerVo triggerVo : job.getTriggerVos()) {
            CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(triggerVo.getCronExpression());
            // .withMisfireHandlingInstructionFireAndProceed() 描述如果错过触发时间如何处理
            scheduler.scheduleJob(TriggerBuilder.newTrigger().withIdentity(triggerVo.getTriggerName(), triggerVo.getTriggerGroup())// 触发器key
                    .startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
                    .withDescription(triggerVo.getTriggerDescription())
                    .withSchedule(schedBuilder)
                    .forJob(JobKey.jobKey(job.getJobName(), job.getJobGroup()))
                    .startNow().build());
        }
    }

    /**
     * 为job添加监听器
     * @param job
     * @throws SchedulerException
     */
    @SneakyThrows
    public void addListenerForJob(JobVo job) {
        log.info("添加JOB监听器：jobName：{} jobGroup：{} jobListener：{}", job.getJobName(), job.getJobGroup(), job.getJobListener());
        ListenerManager listenerManager = scheduler.getListenerManager();
        Matcher<JobKey> matcher = KeyMatcher.keyEquals(JobKey.jobKey(job.getJobName(),job.getJobGroup()));

        // 获取类示例检查类型，转换类型
        Object listener = Class.forName(job.getJobListener()).getDeclaredConstructor().newInstance();
        if(!(listener instanceof JobListener))
            throw new BusinessException(ResultCodeEnum.CLASS_TYPE_NOT_EXTEND_JOB_LISTENER.getCode(),StrUtil.format(ResultCodeEnum.CLASS_TYPE_NOT_EXTEND_JOB_LISTENER.getMessage(),job.getJobListener()));
        // 添加监听器到对应的job上
        listenerManager.addJobListener((JobListener) listener,matcher);
    }

    /**
     * 为job解除监听器
     * @param job
     */
    public boolean unListenerForJob(JobVo job) throws SchedulerException {
        ListenerManager listenerManager = scheduler.getListenerManager();
        return listenerManager.removeJobListener(job.getJobListener());
    }

    /**
     * 接触job触发器绑定，从调度程序中删除指示的Trigger
     * @param triggerVos
     * @throws SchedulerException
     */
    public void unScheduleJob(List<TriggerVo> triggerVos) throws SchedulerException {
        // 添加所有触发器
        for (TriggerVo triggerVo : triggerVos) {
            scheduler.unscheduleJob(TriggerKey.triggerKey(triggerVo.getTriggerName(),triggerVo.getTriggerGroup()));
        }
    }

    /**
     *
     * 删除（删除）具有给定键的Trigger ，并存储新的给定键
     * - 它必须与相同的作业相关联（新触发器必须具有指定的作业名称和组）
     * - 但是，新触发器不必具有相同的名称为旧触发器。
     * @param job
     * @throws SchedulerException
     */
    public void reScheduleJob(JobVo job) throws SchedulerException {
        // 更新所有触发器
        for (TriggerVo triggerVo : job.getTriggerVos()) {
            scheduler.rescheduleJob(TriggerKey.triggerKey(triggerVo.getTriggerName(),triggerVo.getTriggerGroup()),
                    TriggerBuilder.newTrigger().withIdentity(triggerVo.getTriggerName(), triggerVo.getTriggerGroup())// 触发器key
                            .startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
                            .withDescription(triggerVo.getTriggerDescription())
                            .withSchedule(CronScheduleBuilder.cronSchedule(triggerVo.getCronExpression()))
                            .forJob(JobKey.jobKey(job.getJobName(), job.getJobGroup()))
                            .startNow().build());
        }
    }

    /**
     * 获取job组名称
     * @return
     */
    public List<String> jobGroupList() throws SchedulerException {
        return scheduler.getJobGroupNames();
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    public List<JobVo> getJobList(JobVo jobVo) throws SchedulerException {

        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        if (StrUtil.isNotBlank(jobVo.getJobGroup())) {
            matcher = GroupMatcher.jobGroupEquals(jobVo.getJobGroup());
        }

        // 获取job列表
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<JobVo> jobList = new ArrayList<>();
        // 遍历job
        for (JobKey jobKey : jobKeys) {
            jobList.add(buildJobDetail(scheduler.getJobDetail(jobKey)));
        }
        return jobList;
    }

    /**
     * 获取job下的触发器
     * @param jobVo
     * @return
     */
    public List<TriggerVo> getTriggersOfJob(JobVo jobVo) throws SchedulerException {
        // 获取触发器列表
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(JobKey.jobKey(jobVo.getJobName(),jobVo.getJobGroup()));

        return triggers.stream().map(trigger -> {
                // 获取触发器执行状态
            try {
                return buildTriggerDetail(trigger);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public List<JobVo> getRunningJob() throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        return executingJobs.stream().map(executingJob -> {
            try {
                JobDetail jobDetail = executingJob.getJobDetail();
                return buildJobDetail(jobDetail);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
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
     * 立即执行job
     *
     * @param task
     * @throws SchedulerException
     */
    public void runJobNow(JobVo task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.triggerJob(jobKey);
    }


    @NotNull
    private JobVo buildJobDetail(JobDetail jobDetail) throws SchedulerException {
        JobVo job = new JobVo();
        // 获取job明细
        job.setJobName(jobDetail.getKey().getName());
        job.setJobGroup(jobDetail.getKey().getGroup());
        job.setBeanClass(jobDetail.getJobClass().getName());
        job.setDescription(jobDetail.getDescription());
        job.setShouldRecover(jobDetail.requestsRecovery());
        job.setJobDataMap(jobDetail.getJobDataMap());
        job.setShouldRecover(jobDetail.requestsRecovery());
        return job;
    }

    @NotNull
    private TriggerVo buildTriggerDetail(Trigger trigger) throws SchedulerException {
        TriggerVo triggerVo = new TriggerVo();
        triggerVo.setTriggerName(trigger.getKey().getName());
        triggerVo.setTriggerGroup(trigger.getKey().getGroup());
        triggerVo.setTriggerDescription(trigger.getDescription());
        triggerVo.setMayFireAgain(trigger.mayFireAgain());
        triggerVo.setFinalFireTime(trigger.getFinalFireTime());
        triggerVo.setNextFireTime(trigger.getNextFireTime());
        triggerVo.setPreviousFireTime(trigger.getPreviousFireTime());
        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
        triggerVo.setStatus(triggerState.name());
        if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            triggerVo.setCronExpression(cronTrigger.getCronExpression());
        }
        return triggerVo;
    }

}
