package com.sang.system.controller.dict;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.common.domain.dict.param.DataDictionaryQry;
import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.system.service.dict.DictionaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典类
 */
@RestController
@RequestMapping("/lumen/dict")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    /**
     * 分页查询
     * @param dataDictionaryQry
     * @return
     */
    @PostMapping("/dictionaries")
    public PageResult<Dictionary> list(@RequestBody DataDictionaryQry dataDictionaryQry) {
        return PageResult.ok(dictionaryService.dictionaryList(dataDictionaryQry));
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/dictionary")
    public Result<Dictionary> findOne(@RequestParam("id") Long id) {
        return Result.ok(dictionaryService.findOne(id));
    }

    /**
     * 保存
     * @param dictionary
     * @return
     */
    @PostMapping("/dictionary")
    public Result<Boolean> save(@RequestBody Dictionary dictionary) {
        dictionaryService.save(dictionary);
        return Result.ok();
    }

    /**
     * 通过id更新
     * @param dictionary
     * @return
     */
    @PutMapping("/dictionary")
    public Result<Boolean> update(@RequestBody Dictionary dictionary) {
        dictionaryService.update(dictionary);
        return Result.ok();
    }

    /**
     * 通过id删除
     * @param dictionary
     * @return
     */
    @DeleteMapping("/dictionary")
    public Result<Boolean> delete(@RequestBody Dictionary dictionary) {
        dictionaryService.delete(dictionary);
        return Result.ok();
    }

    /**
     * 列表批量删除
     * @param dictionaries
     * @return
     */
    @DeleteMapping("/dictionaries")
    public Result<Boolean> delete(@RequestBody List<Dictionary> dictionaries) {
        dictionaryService.deleteAll(dictionaries);
        return Result.ok();
    }

}
