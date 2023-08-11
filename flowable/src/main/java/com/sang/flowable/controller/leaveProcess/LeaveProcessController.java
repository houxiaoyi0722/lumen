package com.sang.flowable.controller.leaveProcess;

import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.domain.leaveProcess.mapper.LeaveProcessMapper;
import com.sang.common.domain.leaveProcess.entity.LeaveProcess;
import com.sang.common.domain.leaveProcess.param.LeaveProcessQry;
import com.sang.common.domain.leaveProcess.dto.LeaveProcessDto;
import com.sang.flowable.service.leaveProcess.LeaveProcessService;
import org.springframework.web.bind.annotation.*;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import org.springframework.validation.annotation.Validated;
import io.ebean.PagedList;

import javax.annotation.Resource;
import java.util.List;

/**
 * 请假流程
 * 请假流程
 *
 * hxy 2023-06-09 14:32:57
 */
@Validated
@RestController
@RequestMapping("/leaveProcess")
public class LeaveProcessController {

    private final LeaveProcessMapper leaveProcessMapper = LeaveProcessMapper.mapper;

    @Resource
    private LeaveProcessService leaveProcessService;

    /**
     * 分页查询
     *
     * @param qry
     * @return
     */
    @PostMapping("/leaveProcess/qry")
    public PageResult<LeaveProcessDto> list(@RequestBody LeaveProcessQry qry) {
        PagedList<LeaveProcess> pagedList = leaveProcessService.leaveProcessList(qry);
        // 查询全部字段时可不转换直接给pagedList
        return PageResult.ok(leaveProcessMapper.leaveProcessToDtoList(pagedList.getList()), pagedList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/leaveProcess")
    public Result<LeaveProcess> findOne(@RequestParam("id") Long id) {
        return Result.ok(leaveProcessService.findOne(id));
    }

    /**
     * 保存
     *
     * @param leaveProcess
     * @return
     */
    @PostMapping("/leaveProcess")
    public Result<Boolean> save(@RequestBody @Validated(Create.class) LeaveProcessDto leaveProcess) {
        leaveProcessService.save(leaveProcessMapper.dtoToLeaveProcess(leaveProcess));
        return Result.ok();
    }

    /**
    * 批量保存
    * @param leaveProcesss
    * @return
    */
    @PostMapping("/leaveProcesss")
    public Result<Boolean> saveAll(@RequestBody @Validated(Create.class) List<LeaveProcessDto> leaveProcesss) {
        leaveProcessService.saveAll(leaveProcessMapper.dtoToLeaveProcessList(leaveProcesss));
        return Result.ok();
    }

    /**
     * 通过id更新
     *
     * @param leaveProcess
     * @return
     */
    @PutMapping("/leaveProcess")
    public Result<Boolean> update(@RequestBody @Validated(Update.class) LeaveProcessDto leaveProcess) {
        leaveProcessService.update(leaveProcessMapper.dtoToLeaveProcess(leaveProcess));
        return Result.ok();
    }

    /**
     * 通过id删除
     *
     * @param leaveProcess
     * @return
     */
    @DeleteMapping("/leaveProcess")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) LeaveProcessDto leaveProcess) {
        leaveProcessService.delete(leaveProcessMapper.dtoToLeaveProcess(leaveProcess));
        return Result.ok();
    }

    /**
     * 列表批量删除
     *
     * @param leaveProcesss
     * @return
     */
    @DeleteMapping("/leaveProcesss")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) List<LeaveProcessDto> leaveProcesss) {
        leaveProcessService.deleteAll(leaveProcessMapper.dtoToLeaveProcessList(leaveProcesss));
        return Result.ok();
    }


    /**
     * 开启流程
     * @param leaveProcess
     * @return
     */
    @PostMapping("/startProcess")
    public Result<LeaveProcess> startProcess(@RequestBody @Validated(Create.class) LeaveProcessDto leaveProcess) {
        LeaveProcess process = leaveProcessService.startBusinessProcessing(leaveProcessMapper.dtoToLeaveProcess(leaveProcess));
        return Result.ok(process);
    }

    /**
     * 处理任务
     * @param leaveProcess
     * @return
     */
    @PostMapping("/completeTask")
    public Result<Boolean> completeTask(@RequestBody @Validated(Create.class) LeaveProcessDto leaveProcess) {
        leaveProcessService.completeTaskBusinessProcessing(leaveProcess);
        return Result.ok();
    }

    /**
     * 删除任务
     * @param leaveProcess
     * @return
     */
    @DeleteMapping("/deleteProcessInstance")
    public Result<Boolean> deleteProcessInstance(@RequestBody @Validated(Create.class) LeaveProcessDto leaveProcess) {
        return Result.ok(leaveProcessService.deleteProcessInstanceBusinessProcessing(leaveProcessMapper.dtoToLeaveProcess(leaveProcess),leaveProcess));
    }

    /**
     * 审批节点移动- 驳回-退回
     * @param leaveProcess
     * @return
     */
    @PutMapping("/moveActivity")
    public Result<Boolean> moveActivity(@RequestBody @Validated(Create.class) LeaveProcessDto leaveProcess) {
        leaveProcessService.moveActivityBusinessProcessing(leaveProcess);
        return Result.ok();
    }

}
