package com.sang.system.controller.dict;

import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.system.domain.dict.param.DataDictionaryQry;
import com.sang.system.service.dict.DictionaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/lumen/dict")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @GetMapping("/dictionaries")
    public PageResult<Dictionary> list(@RequestBody DataDictionaryQry dataDictionaryQry) {
        return PageResult.ok(dictionaryService.dictionaryList(dataDictionaryQry));
    }

    @GetMapping("/dictionary")
    public Result<Dictionary> findOne(@RequestParam("id") Long id) {
        return Result.ok(dictionaryService.findOne(id));
    }

    @PostMapping("/dictionary")
    public Result<Boolean> save(@RequestBody Dictionary dictionary) {
        dictionaryService.save(dictionary);
        return Result.ok();
    }

    @PutMapping("/dictionary")
    public Result<Boolean> update(@RequestBody Dictionary dictionary) {
        dictionaryService.update(dictionary);
        return Result.ok();
    }

    @DeleteMapping("/dictionary")
    public Result<Boolean> delete(@RequestBody Dictionary dictionary) {
        dictionaryService.delete(dictionary);
        return Result.ok();
    }

    @DeleteMapping("/dictionaries")
    public Result<Boolean> delete(@RequestBody List<Dictionary> dictionaries) {
        dictionaryService.deleteAll(dictionaries);
        return Result.ok();
    }

}
