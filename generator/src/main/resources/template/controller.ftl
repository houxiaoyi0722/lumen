package com.sang.system.controller.${domain};

import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.domain.${domain}.mapper.${model}Mapper;
import com.sang.common.domain.${domain}.entity.${model};
import com.sang.common.domain.${domain}.param.${model}Qry;
import com.sang.system.service.${domain}.${model}Service;
import com.sang.common.domain.${domain}.dto.${model}Dto;
import org.springframework.web.bind.annotation.*;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import org.springframework.validation.annotation.Validated;
import io.ebean.PagedList;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * @author ${author}
 * @since ${createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
@Validated
@RestController
@RequestMapping("/${domain?lower_case}<#if domain?upper_case != model?upper_case>/${model?lower_case}</#if>")
public class ${model}Controller {

    private static final ${model}Mapper ${model?lower_case}Mapper = ${model}Mapper.mapper;

    @Resource
    private ${model}Service ${model?lower_case}Service;

    /**
     * 分页查询
     *
     * @param qry
     * @return
     */
    @PostMapping("/${model?lower_case}s/qry")
    public PageResult<${model}Dto> list(@RequestBody ${model}Qry qry) {
        PagedList<${model}> pagedList = ${model?lower_case}Service.${model?lower_case}List(qry);
        // 查询全部字段时可不转换直接给pagedList
        return PageResult.ok(${model?lower_case}Mapper.${model?lower_case}ToDtoList(pagedList.getList()), pagedList);
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
    public Result<Boolean> save(@RequestBody @Validated(Create.class) ${model}Dto ${model?lower_case}) {
        ${model?lower_case}Service.save(${model?lower_case}Mapper.dtoTo${model}(${model?lower_case}));
        return Result.ok();
    }

    /**
    * 批量保存
    * @param ${model?lower_case}s
    * @return
    */
    @PostMapping("/${model?lower_case}s")
    public Result<Boolean> saveAll(@RequestBody @Validated(Create.class) List<${model}Dto> ${model?lower_case}s) {
        ${model?lower_case}Service.saveAll(${model?lower_case}Mapper.dtoTo${model}List(${model?lower_case}s));
        return Result.ok();
    }

    /**
     * 通过id更新
     *
     * @param ${model?lower_case}
     * @return
     */
    @PutMapping("/${model?lower_case}")
    public Result<Boolean> update(@RequestBody @Validated(Update.class) ${model}Dto ${model?lower_case}) {
        ${model?lower_case}Service.update(${model?lower_case}Mapper.dtoTo${model}(${model?lower_case}));
        return Result.ok();
    }

    /**
     * 通过id删除
     *
     * @param ${model?lower_case}
     * @return
     */
    @DeleteMapping("/${model?lower_case}")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) ${model}Dto ${model?lower_case}) {
        ${model?lower_case}Service.delete(${model?lower_case}Mapper.dtoTo${model}(${model?lower_case}));
        return Result.ok();
    }

    /**
     * 列表批量删除
     *
     * @param ${model?lower_case}s
     * @return
     */
    @DeleteMapping("/${model?lower_case}s")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) List<${model}Dto> ${model?lower_case}s) {
        ${model?lower_case}Service.deleteAll(${model?lower_case}Mapper.dtoTo${model}List(${model?lower_case}s));
        return Result.ok();
    }
}
