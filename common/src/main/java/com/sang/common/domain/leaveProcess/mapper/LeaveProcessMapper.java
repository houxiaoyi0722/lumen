package com.sang.common.domain.leaveProcess.mapper;

import com.sang.common.domain.leaveProcess.dto.LeaveProcessDto;
import com.sang.common.domain.leaveProcess.entity.LeaveProcess;
import org.mapstruct.Mapper;
import org.mapstruct.Builder;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 * 请假流程
 * 请假流程
 * 模型映射类
 * hxy 2023-06-09 14:32:57
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface LeaveProcessMapper {

    LeaveProcessMapper mapper = Mappers.getMapper( LeaveProcessMapper.class );

    List<LeaveProcessDto> leaveProcessToDtoList(List<LeaveProcess> list);

    List<LeaveProcess> dtoToLeaveProcessList(List<LeaveProcessDto> list);

    LeaveProcess dtoToLeaveProcess(LeaveProcessDto leaveProcessDto);

    LeaveProcessDto leaveProcessToDto(LeaveProcess leaveProcess);

}
