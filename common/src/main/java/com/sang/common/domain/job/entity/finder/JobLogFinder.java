package com.sang.common.domain.job.entity.finder;

import com.sang.common.domain.job.entity.JobLog;
import io.ebean.Finder;
import lombok.Builder;

/**
 * Job管理
 * Job执行日志
 * finder
 * hxy 2022-08-29 14:25:42
 */
@Builder
public class JobLogFinder extends Finder<Long, JobLog> {

    /**
    * Construct using the default EbeanServer.
    */
    public JobLogFinder() {
        super(JobLog.class);
    }

}

