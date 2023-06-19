package com.sang.service.leaveProcess;

import com.sang.common.domain.leaveProcess.entity.LeaveProcess;
import com.sang.common.domain.leaveProcess.param.LeaveProcessQry;
import io.ebean.PagedList;

import java.util.List;

/**
 * 请假流程
 * 请假流程
 *
 * hxy 2023-06-09 14:32:57
 */
public interface LeaveProcessService {

    PagedList<LeaveProcess> leaveProcessList(LeaveProcessQry qry);

    LeaveProcess findOne(Long id);

    void save(LeaveProcess leaveProcess);

    void saveAll(List<LeaveProcess> leaveProcesss);

    void insert(LeaveProcess leaveProcess);

    void update(LeaveProcess leaveProcess);

    void delete(LeaveProcess leaveProcess);

    void deleteAll(List<LeaveProcess> leaveProcesss);

    void startProcess(LeaveProcess dtoToLeaveProcess);
}
