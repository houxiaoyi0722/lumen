package com.sang.system.service.job.impl;

import com.sang.common.domain.job.entity.JobLog;
import com.sang.common.domain.job.param.JobLogQry;
import com.sang.common.domain.job.repo.JobLogRepository;
import com.sang.system.service.job.JobLogService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Job管理
 * Job执行日志
 * 
 * hxy 2022-08-29 14:25:42
 */
@Slf4j
@Service
public class JobLogServiceImpl implements JobLogService {

    @Resource
    private JobLogRepository repository;

    @Override
    public PagedList<JobLog> joblogList(JobLogQry qry) {
        return repository.getPage(qry);
    }

    @Override
    public JobLog findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(JobLog joblog) {
        joblog.save();
    }

    @Override
    @Transactional
    public void insert(JobLog joblog) {
        joblog.insert();
    }

    @Override
    @Transactional
    public void update(JobLog joblog) {
        joblog.update();
    }

    @Override
    @Transactional
    public void delete(JobLog joblog) {
        repository.delete(joblog);
    }

    @Override
    @Transactional
    public void saveAll(List<JobLog> JobLogs) {
        repository.saveAll(JobLogs);
    }

    @Override
    @Transactional
    public void deleteAll(List<JobLog> joblogs) {
        repository.deleteAll(joblogs);
    }
}