package com.sang.common.domain.job.entity;

import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.job.entity.finder.JobLogFinder;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.common.validate.job.TriggerDelete;
import com.sang.common.validate.job.TriggerSave;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.DbJson;
import io.ebean.annotation.Index;
import io.ebean.annotation.WhenModified;
import lombok.*;
import org.quartz.JobDataMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Date;

/**
 * Job管理
 * Job执行日志
 * 数据模型
 * hxy 2022-08-29 14:25:42
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Index(columnNames = "job_name")
@Index(columnNames = "job_group")
@Index(columnNames = "status")
@DbComment("JobLog执行日志表")
public class JobLog extends BaseModel {

    public static final JobLogFinder finder = JobLogFinder.builder().build();

    /**
     * job名称
     */
    @DbComment("job名称")
    @Column(length = 190)
    private String jobName;
    /**
     * job分组
     */
    @DbComment("job分组")
    @Column(length = 190)
    private String jobGroup;
    /**
     * 执行类全限定名
     */
    @DbComment("执行类全限定名")
    @Column(length = 250)
    private String beanClass;
    /**
     * 要传给job 的参数
     */
    @DbComment("执行类全限定名")
    @DbJson
    private JobDataMap jobDataMap;
    /**
     * cron
     */
    @DbComment("cron")
    @Column(length = 120)
    private String cronExpression;
    /**
     * 状态
     */
    @DbComment("状态")
    @Column(length = 30)
    private String status;
    /**
     * 开始时间
     */
    @DbComment("开始时间")
    @Column
    private Date startTime;
    /**
     * 结束时间
     */
    @DbComment("结束时间")
    @Column
    private Date endTime;
    /**
     * 触发器是否会再次触发
     */
    @DbComment("触发器是否会再次触发")
    @Column
    private Boolean mayFireAgain;

    /**
     * job执行时间,以毫秒为单位
     */
    @DbComment("job执行时间,以毫秒为单位")
    @Column
    private Long jobRunTime;

    /**
     * job执行结果\错误结果
     */
    @DbComment("job执行结果/错误结果")
    @DbJson
    private Object result;
}
