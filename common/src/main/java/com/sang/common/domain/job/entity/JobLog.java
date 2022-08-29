package com.sang.common.domain.job.entity;

import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.job.entity.finder.JobLogFinder;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

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
@MappedSuperclass
@Entity
@Table
@DbComment("JobLog执行日志表")
public class JobLog extends BaseModel {

    public static final JobLogFinder finder = JobLogFinder.builder().build();

}
