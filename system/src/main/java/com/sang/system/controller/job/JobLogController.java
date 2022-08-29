package com.sang.system.controller.job;

import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.domain.job.mapper.JobLogMapper;
import com.sang.common.domain.job.entity.JobLog;
import com.sang.common.domain.job.param.JobLogQry;
import com.sang.system.service.job.JobLogService;
import com.sang.common.domain.job.dto.JobLogDto;
import org.springframework.web.bind.annotation.*;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import org.springframework.validation.annotation.Validated;
import io.ebean.PagedList;

import javax.annotation.Resource;
import java.util.List;

/**
 * Job管理
 * Job执行日志
 * 
 * hxy 2022-08-29 14:25:42
 */
@Validated
@RestController
@RequestMapping("/lumen/job/log")
public class JobLogController {

    private final JobLogMapper joblogMapper = JobLogMapper.mapper;

    @Resource
    private JobLogService joblogService;

    /**
     * 分页查询
     *
     * @param qry
     * @return
     */
    @PostMapping("/joblogs")
    public PageResult<JobLogDto> list(@RequestBody JobLogQry qry) {
        PagedList<JobLog> pagedList = joblogService.joblogList(qry);
        // 查询全部字段时可不转换直接给pagedList
        return PageResult.ok(joblogMapper.joblogToDtoList(pagedList.getList()), pagedList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/joblog")
    public Result<JobLog> findOne(@RequestParam("id") Long id) {
        return Result.ok(joblogService.findOne(id));
    }

    /**
     * 通过id删除
     *
     * @param joblog
     * @return
     */
    @DeleteMapping("/joblog")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) JobLogDto joblog) {
        joblogService.delete(joblogMapper.dtoToJobLog(joblog));
        return Result.ok();
    }

    /**
     * 列表批量删除
     *
     * @param joblogs
     * @return
     */
    @DeleteMapping("/joblogs")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) List<JobLogDto> joblogs) {
        joblogService.deleteAll(joblogMapper.dtoToJobLogList(joblogs));
        return Result.ok();
    }
}