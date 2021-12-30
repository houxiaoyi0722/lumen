package com.sang.system.controller.dict;

import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.system.param.dict.DataDictionaryParam;
import com.sang.system.service.dict.DictionaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/lumen/matedata")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @GetMapping("/dictionaries")
    public PageResult<Dictionary> list(@RequestBody DataDictionaryParam dataDictionaryParam) {
        return PageResult.ok(dictionaryService.dictionaryList(dataDictionaryParam));
    }

    @GetMapping("/dictionary")
    public Result<Dictionary> findOne(@RequestParam("id") String id) {
        return Result.ok(dictionaryService.findOne(id));
    }

    @PostMapping("/dictionary")
    public Result<Dictionary> save(@RequestBody Dictionary dictionary) {
        return Result.ok(dictionaryService.save(dictionary));
    }

    @PutMapping("/dictionary")
    public Result<Dictionary> update(@RequestBody Dictionary dictionary) {
        return Result.ok(dictionaryService.update(dictionary));
    }

    @DeleteMapping("/dictionary")
    public Result<Boolean> delete(@RequestParam("id") String id) {
        return Result.ok(dictionaryService.delete(id));
    }

}
