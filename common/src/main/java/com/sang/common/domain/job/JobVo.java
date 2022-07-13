package com.sang.common.domain.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.quartz.JobDataMap;

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
     * trigger name
     */
    private String triggerName;
    /**
     * trigger分组
     */
    private String triggerGroup;
    /**
     * 描述
     */
    private String description;
    /**
     * 描述
     */
    private String triggerDescription;
    /**
     * cron
     */
    private String cronExpression;
    /**
     * 执行类全限定名
     */
    private String beanClass;
    /**
     * job状态
     */
    private String jobStatus;
    /**
     * 启用/停用
     */
    private Boolean enable;
    /**
     * 恢复/故障转移 时是否从头开始执行
     */
    private boolean shouldRecover;
    /**
     * job在无关联出发器时是否保留
     */
    private boolean durability;
    /**
     * 要传给job 的参数
     */
    private JobDataMap jobDataMap;

}
