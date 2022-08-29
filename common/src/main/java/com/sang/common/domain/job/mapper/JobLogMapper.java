package com.sang.common.domain.job.mapper;

import com.sang.common.domain.job.dto.JobLogDto;
import com.sang.common.domain.job.entity.JobLog;
import org.mapstruct.Mapper;
import org.mapstruct.Builder;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 * Job管理
 * Job执行日志
 * 模型映射类
 * hxy 2022-08-29 14:25:42
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface JobLogMapper {

    JobLogMapper mapper = Mappers.getMapper( JobLogMapper.class );

    List<JobLogDto> joblogToDtoList(List<JobLog> list);

    List<JobLog> dtoToJobLogList(List<JobLogDto> list);

    JobLog dtoToJobLog(JobLogDto joblogDto);

    JobLogDto joblogToDto(JobLog joblog);

}