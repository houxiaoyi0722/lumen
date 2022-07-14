package com.sang.common.domain.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TriggerVo {
    /**
     * 描述
     */
    private String triggerDescription;
    /**
     * cron
     */
    private String cronExpression;
    /**
     * job状态
     */
    private String jobStatus;
}
