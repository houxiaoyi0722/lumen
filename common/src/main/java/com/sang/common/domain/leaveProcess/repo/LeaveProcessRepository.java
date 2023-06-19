package com.sang.common.domain.leaveProcess.repo;

import com.sang.common.domain.leaveProcess.entity.LeaveProcess;
import com.sang.common.domain.leaveProcess.param.LeaveProcessQry;
import com.sang.common.domain.leaveProcess.entity.query.QLeaveProcess;
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.springframework.stereotype.Repository;

/**
 * 请假流程
 * 请假流程
 * Repository
 * hxy 2023-06-09 14:32:57
 */
@Repository
public class LeaveProcessRepository extends BeanRepository<Long, LeaveProcess> {

    protected LeaveProcessRepository(Database server) {
        super(LeaveProcess.class, server);
    }

    public PagedList<LeaveProcess> getPage(LeaveProcessQry qry) {
        QLeaveProcess alice = QLeaveProcess.alias();

        return new QLeaveProcess()
            .select()
            .setFirstRow(qry.getStartPosition())
            .setMaxRows(qry.getEndPosition())
            .orderBy().whenCreated.desc()
            .findPagedList();
    }


}
