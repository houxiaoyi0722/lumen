package com.sang.common.listener;

import com.sang.common.domain.job.vo.JobVo;
import com.sang.common.job.QuartzManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.quartz.Scheduler;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private QuartzManager quartzManager;

    @Resource
    private Scheduler scheduler;

    @SneakyThrows
    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
        // todo 监听器配置
        // 在初始化时添加监听器
        List<JobVo> jobVos = List.of(JobVo.builder().jobName("demo1").jobGroup("demo").jobListener("com.sang.system.job.listener.DemoJobListener").build());
        jobVos.forEach(quartzManager::addListenerForJob);
        // 全局监听器
        scheduler.getListenerManager().addJobListener(new GlobalJobListener());
    }
}