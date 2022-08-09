package com.sang.system.controller.${domain?lower_case};

import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.domain.${domain?lower_case}.mapper.${model}Mapper;
import com.sang.common.domain.${domain?lower_case}.entity.${model};
import com.sang.common.domain.${domain?lower_case}.param.${model}Qry;
import com.sang.system.service.${domain?lower_case}.${model}Service;
import com.sang.common.domain.${domain?lower_case}.dto.${model}Dto;
import org.springframework.web.bind.annotation.*;
import io.ebean.PagedList;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * ${author} ${createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
@RestController
@RequestMapping("/lumen/${domain?lower_case}<#if domain?upper_case != model?upper_case>/${model?lower_case}</#if>")
public class ${model}Controller {

    @Resource
    private ${model}Service ${model?lower_case}Service;

    private ${model}Mapper mapper = ${model}Mapper.mapper;

    /**
     * 分页查询
     *
     * @param qry
     * @return
     */
    @PostMapping("/${model?lower_case}s")
    public PageResult<${model}Dto> list(@RequestBody ${model}Qry qry) {
        PagedList<${model}> pagedList = ${model?lower_case}Service.${model?lower_case}List(qry);
        // 查询全部字段时可不转换直接给pagedList
        return PageResult.ok(mapper.${model?lower_case}ToDto(pagedList.getList()), pagedList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/${model?lower_case}")
    public Result<${model}> findOne(@RequestParam("id") Long id) {
        return Result.ok(${model?lower_case}Service.findOne(id));
    }

    /**
     * 保存
     *
     * @param ${model?lower_case}
     * @return
     */
    @PostMapping("/${model?lower_case}")
    public Result<Boolean> save(@RequestBody ${model} ${model?lower_case}) {
        ${model?lower_case}Service.save(${model?lower_case});
        return Result.ok();
    }

    /**
     * 通过id更新
     *
     * @param ${model?lower_case}
     * @return
     */
    @PutMapping("/${model?lower_case}")
    public Result<Boolean> update(@RequestBody ${model} ${model?lower_case}) {
        ${model?lower_case}Service.update(${model?lower_case});
        return Result.ok();
    }

    /**
     * 通过id删除
     *
     * @param ${model?lower_case}
     * @return
     */
    @DeleteMapping("/${model?lower_case}")
    public Result<Boolean> delete(@RequestBody ${model} ${model?lower_case}) {
        ${model?lower_case}Service.delete(${model?lower_case});
        return Result.ok();
    }

    /**
     * 列表批量删除
     *
     * @param ${model?lower_case}s
     * @return
     */
    @DeleteMapping("/${model?lower_case}s")
    public Result<Boolean> delete(@RequestBody List<${model}> ${model?lower_case}s) {
        ${model?lower_case}Service.deleteAll(${model?lower_case}s);
        return Result.ok();
    }
}