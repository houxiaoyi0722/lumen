package com.sang.system.service.job;

import com.sang.common.domain.job.entity.JobLog;
import com.sang.common.domain.job.param.JobLogQry;
import io.ebean.PagedList;

import java.util.List;

/**
 * Job管理
 * Job执行日志
 * 
 * hxy 2022-08-29 14:25:42
 */
public interface JobLogService {

    PagedList<JobLog> joblogList(JobLogQry qry);

    JobLog findOne(Long id);

    void save(JobLog joblog);

    void saveAll(List<JobLog> joblogs);

    void insert(JobLog joblog);

    void update(JobLog joblog);

    void delete(JobLog joblog);

    void deleteAll(List<JobLog> joblogs);

    int limitJobLogSize();

}