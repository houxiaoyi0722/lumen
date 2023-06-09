package com.sang.common.domain.leaveProcess.entity;

import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.leaveProcess.entity.finder.LeaveProcessFinder;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.util.Date;

/**
 * 请假流程
 * 请假流程
 * 数据模型
 * hxy 2023-06-09 14:32:57
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Entity
@Table
@DbComment("请假流程表")
public class LeaveProcess extends BaseModel {

    public static final LeaveProcessFinder finder = LeaveProcessFinder.builder().build();

    @DbComment("申请人")
    @Column(length = 50)
    private String name;

    @DbComment("请假时长")
    @Column(length = 50)
    private String day;

    @DbComment("开始时间")
    @Column
    private Date startTime;

    @DbComment("结束时间")
    @Column
    private Date endTime;

    @DbComment("请假原因")
    @Column(length = 500)
    private String reason;

    @DbComment("假期类型 年假/事假/育儿假/产假/病假/调休")
    @Column(length = 20)
    private String type;

    @DbComment("流程定义id")
    @Column(length = 100)
    private String processDefinitionId;

    @DbComment("流程实例id")
    @Column(length = 100)
    private String processInstanceId;

}
