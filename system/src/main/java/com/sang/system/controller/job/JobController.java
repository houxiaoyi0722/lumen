package com.sang.system.controller.job;

import com.sang.common.domain.job.JobVo;
import com.sang.common.exception.BusinessException;
import com.sang.common.job.QuartzManager;
import com.sang.common.response.Result;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * job管理
 * @author hxy
 * @date 2021/12/31 17:57
 **/
@RestController
@RequestMapping("/lumen/job")
public class JobController {

    @Resource
    private QuartzManager quartzManager;

    /**
     * 添加job
     * @param jobVo
     * @return
     */
    @PutMapping("/addJob")
    public Result addJob(@RequestBody JobVo jobVo) throws BusinessException, SchedulerException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        quartzManager.addJob(jobVo);
        return Result.ok();
    }

    /**
     * 获取全部job
     * @param
     * @return
     */
    @GetMapping("/getAllJob")
    public Result<List<JobVo>> getAllJob() throws SchedulerException {
        return Result.ok(quartzManager.getAllJob());
    }



}
