package com.sang.common.listener;

import com.sang.common.domain.job.JobVo;
import com.sang.common.job.QuartzManager;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
        // 在初始化时添加监听器
        List<JobVo> jobVos = List.of(JobVo.builder().jobName("demo1").jobGroup("demo").jobListener("com.sang.system.job.listener.DemoJobListener").build());
        jobVos.forEach(quartzManager::addListenerForJob);
    }
}