package com.sang.system.controller.job;

import com.sang.common.domain.job.vo.JobVo;
import com.sang.common.domain.job.vo.TriggerVo;
import com.sang.common.job.QuartzManager;
import com.sang.common.response.Result;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.common.validate.job.TriggerSave;
import com.sang.common.validate.job.TriggerDelete;
import org.quartz.SchedulerException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * job管理
 * @author hxy
 * @date 2021/12/31 17:57
 **/
@Validated
@RestController
@RequestMapping("/task")
public class JobController {

    @Resource
    private QuartzManager quartzManager;

    /**
     * 添加或者更新job
     * @param jobVo
     * @return
     */
    @PutMapping("/job")
    public Result<String> saveJob(@RequestBody @Validated({Create.class, Update.class}) JobVo jobVo) {
        quartzManager.saveJob(jobVo);
        return Result.ok("成功");
    }

    /**
     * 删除job
     * @param jobVo
     * @return
     * @throws SchedulerException
     */
    @DeleteMapping("/job")
    public Result<String> deleteJob(@RequestBody @Validated(Delete.class) JobVo jobVo) throws SchedulerException {
        quartzManager.deleteJob(jobVo);
        return Result.ok("成功");
    }

    /**
     * 添加触发器
     * @param jobVo
     * @return
     * @throws SchedulerException
     */
    @PostMapping("/triggers")
    public Result<String> addTriggersForJob(@RequestBody @Validated(TriggerSave.class) JobVo jobVo) throws SchedulerException {
        quartzManager.addTriggersForJob(jobVo);
        return Result.ok("成功");
    }

    /**
     * 删除对应的触发器
     * @param triggerVos 触发器列表，需要name 和group 参数
     * @return
     * @throws SchedulerException
     */
    @DeleteMapping("/triggers")
    public Result<String> unScheduleJob(@RequestBody @Validated(TriggerDelete.class) List<TriggerVo> triggerVos) throws SchedulerException {
        quartzManager.unScheduleJob(triggerVos);
        return Result.ok("成功");
    }

    /**
     * 更新job对应的触发器
     * @param jobVo
     * @return
     * @throws SchedulerException
     */
    @PutMapping("/triggers")
    public Result<String> reScheduleJob(@RequestBody @Validated(TriggerSave.class) JobVo jobVo) throws SchedulerException {
        quartzManager.reScheduleJob(jobVo);
        return Result.ok("成功");
    }

    /**
     * 获取所有job group
     * @return
     * @throws SchedulerException
     */
    @GetMapping("/job/groups")
    public Result<List<String>> jobGroupList() throws SchedulerException {
        return Result.ok(quartzManager.jobGroupList());
    }

    /**
     * 按照jobgroup 获取job列表，group为空获取全部
     * @param jobVo 输入job group 查询
     * @return
     */
    @GetMapping("/getJobList")
    public Result<List<JobVo>> getJobList(@RequestBody JobVo jobVo) throws SchedulerException {
        return Result.ok(quartzManager.getJobList(jobVo));
    }

    /**
     * 获取job下的所有trigger
     * @param jobVo 输入job group name
     * @return
     */
    @PostMapping("/job/triggers")
    public Result<List<TriggerVo>> getTriggersOfJob(@RequestBody JobVo jobVo) throws SchedulerException {
        return Result.ok(quartzManager.getTriggersOfJob(jobVo));
    }

    /**
     * 获取所有正在运行的job
     * @return
     */
    @GetMapping("/running/job")
    public Result<List<JobVo>> getRunningJob() throws SchedulerException {
        return Result.ok(quartzManager.getRunningJob());
    }

    /**
     * 暂停一个job直到调用恢复为止都不会被执行
     * @param jobVo
     * @return
     * @throws SchedulerException
     */
    @PutMapping("/job/pause")
    public Result<String> pauseJob(@RequestBody JobVo jobVo) throws SchedulerException {
        quartzManager.pauseJob(jobVo);
        return Result.ok("成功");
    }

    /**
     * 恢复一个被暂停的job使其可以继续执行
     * @param jobVo
     * @return
     * @throws SchedulerException
     */
    @PutMapping("/job/resume")
    public Result<String> resumeJob(@RequestBody JobVo jobVo) throws SchedulerException {
        quartzManager.resumeJob(jobVo);
        return Result.ok("成功");
    }

    /**
     * 立即执行一个job
     * @param jobVo
     * @throws SchedulerException
     */
    @PutMapping("/job/run")
    public Result<String> runJobNow(@RequestBody JobVo jobVo) throws SchedulerException {
        quartzManager.runJobNow(jobVo);
        return Result.ok("成功");
    }

    /**
     * 添加job监听器
     * @param jobVo
     * @throws SchedulerException
     */
    @PutMapping("/job/listener")
    public Result<String> addListenerForJob(@RequestBody @Validated JobVo jobVo) {
        quartzManager.addListenerForJob(jobVo);
        return Result.ok("成功");
    }

    /**
     * 删除job监听器
     * @param jobVo
     * @throws SchedulerException
     */
    @DeleteMapping("/job/listener")
    public Result<Boolean> unListenerForJob(@RequestBody JobVo jobVo) throws SchedulerException {
        return Result.ok(quartzManager.unListenerForJob(jobVo));
    }



}
