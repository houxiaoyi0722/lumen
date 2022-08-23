package com.sang.system.controller.dict;

import com.sang.common.domain.dict.dto.DictionaryDto;
import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.common.domain.dict.mapper.DictionaryMapper;
import com.sang.common.domain.dict.param.DictionaryQry;
import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.system.service.dict.DictionaryService;
import io.ebean.PagedList;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 数据字典类
 */
@RestController
@Validated
@RequestMapping("/lumen/dict")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    private final DictionaryMapper mapper = DictionaryMapper.mapper;

    /**
     * 分页查询
     * @param dataDictionaryQry
     * @return
     */
    @PostMapping("/dictionaries")
    public PageResult<DictionaryDto> list(@RequestBody DictionaryQry dataDictionaryQry) {
        PagedList<Dictionary> pagedList = dictionaryService.dictionaryList(dataDictionaryQry);
        return PageResult.ok(mapper.dictionaryToDtoList(pagedList.getList()), pagedList);
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/dictionary")
    public Result<Dictionary> findOne(@RequestParam("id") @NotNull(message = "id不能为空") @Min(value = 1L,message = "编号必须大于1") Long id) {
        return Result.ok(dictionaryService.findOne(id));
    }

    /**
     * 保存
     * @param dictionary
     * @return
     */
    @PostMapping("/dictionary")
    public Result<Boolean> save(@RequestBody @Validated(Create.class) DictionaryDto dictionary) {
        dictionaryService.save(mapper.dtoToDictionary(dictionary));
        return Result.ok();
    }

    /**
     * 通过id更新
     * @param dictionary
     * @return
     */
    @PutMapping("/dictionary")
    public Result<Boolean> update(@RequestBody @Validated(Update.class) DictionaryDto dictionary) {
        dictionaryService.update(mapper.dtoToDictionary(dictionary));
        return Result.ok();
    }

    /**
     * 通过id删除
     * @param dictionary
     * @return
     */
    @DeleteMapping("/dictionary")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) DictionaryDto dictionary) {
        dictionaryService.delete(mapper.dtoToDictionary(dictionary));
        return Result.ok();
    }

    /**
     * 列表批量删除
     * @param dictionaries
     * @return
     */
    @DeleteMapping("/dictionaries")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) List<DictionaryDto> dictionaries) {
        dictionaryService.deleteAll(mapper.dtoToDictionaryList(dictionaries));
        return Result.ok();
    }

}
