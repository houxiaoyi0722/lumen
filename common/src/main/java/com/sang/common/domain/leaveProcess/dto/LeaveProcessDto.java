package com.sang.common.domain.leaveProcess.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import io.ebean.annotation.DbComment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 请假流程
 * 请假流程
 * 数据传输对象
 * hxy 2023-06-09 14:32:57
*/
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveProcessDto {

    /**
     * id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {Delete.class,Update.class})
    private Long id;

    /**
     * 申请人
     */
    private String name;
    /**
     * 请假时长
     */
    private String day;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 请假原因
     */
    private String reason;
    /**
     * 假期类型 年假/事假/育儿假/产假/病假/调休
     */
    private String type;
    /**
     * 流程定义id
     */
    private String processDefinitionId;
    /**
     * 流程实例id
     */
    private String processInstanceId;
}
