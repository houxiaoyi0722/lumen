package com.sang.system.controller.dict;

import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.system.domain.dict.param.DataDictionaryParam;
import com.sang.system.service.dict.DictionaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/lumen/dict")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @GetMapping("/dictionaries")
    public PageResult<Dictionary> list(@RequestBody DataDictionaryParam dataDictionaryParam) {
        return PageResult.ok(dictionaryService.dictionaryList(dataDictionaryParam));
    }

    @GetMapping("/dictionary")
    public Result<Dictionary> findOne(@RequestParam("id") Long id) {
        return Result.ok(dictionaryService.findOne(id));
    }

    @PostMapping("/dictionary")
    public Result<Dictionary> save(@RequestBody Dictionary dictionary) {
        dictionaryService.save(dictionary);
        return Result.ok();
    }

    @PutMapping("/dictionary")
    public Result<Dictionary> update(@RequestBody Dictionary dictionary) {
        dictionaryService.update(dictionary);
        return Result.ok();
    }

    @DeleteMapping("/dictionary")
    public Result<Boolean> delete(@RequestParam("id") Long id) {
        dictionaryService.delete(id);
        return Result.ok();
    }

}
