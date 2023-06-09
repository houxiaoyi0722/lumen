package com.sang.common.domain.leaveProcess.entity.finder;

import com.sang.common.domain.leaveProcess.entity.LeaveProcess;
import io.ebean.Finder;
import lombok.Builder;

/**
 * 请假流程
 * 请假流程
 * finder
 * hxy 2023-06-09 14:32:57
 */
@Builder
public class LeaveProcessFinder extends Finder<Long, LeaveProcess> {

    /**
    * Construct using the default EbeanServer.
    */
    public LeaveProcessFinder() {
        super(LeaveProcess.class);
    }

}

