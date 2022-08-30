package com.sang.common.domain.job.repo;

import com.sang.common.constants.StringConst;
import com.sang.common.domain.job.entity.JobLog;
import com.sang.common.domain.job.entity.query.QJobLog;
import com.sang.common.domain.job.param.JobLogQry;
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.quartz.JobDataMap;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;

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


    public void updateJobStatus(JobDataMap jobDataMap, Object result, Date endTime, long jobRunTime, String state) {
        QJobLog alias = QJobLog.alias();
        new QJobLog().id.eq(jobDataMap.getLong(StringConst.JOB_LOG_ID)).asUpdate()
                .set(alias.endTime.toString(),endTime)
                .set(alias.jobRunTime.toString(),jobRunTime)
                .set(alias.status.toString(),state)
                .set(alias.result.toString(),result)
                .update();
    }

    public int findCount() {
        return new QJobLog().findCount();
    }

    public JobLog findByLimit(int limit) {
        return new QJobLog().setFirstRow(limit-1).setMaxRows(limit).orderBy().whenCreated.desc().findOne();
    }

    public int deleteIfCreateBeforeDate(Instant whenCreated) {
        return new QJobLog().whenCreated.before(whenCreated).delete();
    }
}
