package com.sang.system.controller.dict;

import com.sang.response.PageResult;
import com.sang.response.Result;
import com.sang.system.domain.dict.entity.DataDictionary;
import com.sang.system.param.dict.DataDictionaryParam;
import com.sang.system.service.dict.DataDictionaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/lumen/matedata")
public class DataDictionaryController {

    @Resource
    private DataDictionaryService dataDictionaryService;

    @GetMapping("/dictionaries")
    public PageResult<DataDictionary> list(@RequestBody DataDictionaryParam dataDictionaryParam) {
        return PageResult.ok(dataDictionaryService.dictionaryList(dataDictionaryParam));
    }

    @GetMapping("/dictionary")
    public Result<DataDictionary> findOne(@RequestParam("id") String id) {
        return Result.ok(dataDictionaryService.findOne(id));
    }

    @PostMapping("/dictionary")
    public Result<DataDictionary> save(@RequestBody DataDictionary dataDictionary) {
        return Result.ok(dataDictionaryService.save(dataDictionary));
    }

    @PutMapping("/dictionary")
    public Result<DataDictionary> update(@RequestBody DataDictionary dataDictionary) {
        return Result.ok(dataDictionaryService.update(dataDictionary));
    }

    @DeleteMapping("/dictionary")
    public Result<Boolean> delete(@RequestParam("id") String id) {
        return Result.ok(dataDictionaryService.delete(id));
    }

}
