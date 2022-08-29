package com.sang.common.domain.job.vo;

import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.common.validate.job.TriggerSave;
import lombok.*;
import org.quartz.JobDataMap;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * task
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JobVo {
    /**
     * job名称
     */
    @NotBlank(message = "jobName不能为空",groups = {Update.class, Create.class, Delete.class, TriggerSave.class})
    private String jobName;
    /**
     * job分组
     */
    @NotBlank(message = "jobGroup不能为空",groups = {Update.class, Create.class, Delete.class, TriggerSave.class})
    private String jobGroup;
    /**
     * 描述
     */
    private String description;
    /**
     * 执行类全限定名
     */
    @NotBlank(message = "beanClass不能为空",groups = {Update.class, Create.class})
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
    @Valid
    @NotNull(message = "triggerVos不能为空",groups = {TriggerSave.class})
    private List<TriggerVo> triggerVos;

    /**
     * job 监听器
     */
    private String jobListener;

}
