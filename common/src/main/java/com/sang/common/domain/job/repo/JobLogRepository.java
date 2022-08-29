package com.sang.common.domain.job.repo;

import com.sang.common.domain.job.entity.JobLog;
import com.sang.common.domain.job.param.JobLogQry;
import com.sang.common.domain.job.entity.query.QJobLog;
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.springframework.stereotype.Repository;

/**
 * Job管理
 * Job执行日志
 * Repository
 * hxy 2022-08-29 14:25:42
 */
@Repository
public class JobLogRepository extends BeanRepository<Long, JobLog> {

    protected JobLogRepository(Database server) {
        super(JobLog.class, server);
    }

    public PagedList<JobLog> getPage(JobLogQry qry) {
        QJobLog alice = QJobLog.alias();

        return new QJobLog()
            .select()
            .setFirstRow(qry.getStartPosition())
            .setMaxRows(qry.getEndPosition())
            .orderBy().whenCreated.desc()
            .findPagedList();
    }


}
