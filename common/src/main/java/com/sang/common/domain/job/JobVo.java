package com.sang.common.domain.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.quartz.JobDataMap;

import java.util.List;

/**
 * task
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobVo {
    /**
     * job名称
     */
    private String jobName;
    /**
     * job分组
     */
    private String jobGroup;
    /**
     * 描述
     */
    private String description;
    /**
     * 执行类全限定名
     */
    private String beanClass;
    /**
     * 恢复/故障转移 时是否从头开始执行
     */
    private boolean shouldRecover;
    /**
     * 要传给job 的参数
     */
    private JobDataMap jobDataMap;
    /**
     * trigger相关
     */
    private List<TriggerVo> triggerVos;

}
