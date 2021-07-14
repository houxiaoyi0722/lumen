package com.sang.system.controller;

import com.sang.system.domain.entity.DataDictionary;
import com.sang.system.param.DataDictionaryParam;
import com.sang.system.service.DataDictionaryService;
import io.ebean.PagedList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/lumen/matedata")
public class DataDictionaryController {

    @Resource
    private DataDictionaryService dataDictionaryService;

    @GetMapping("/dictionaries")
    public PagedList<DataDictionary> dictionaryList(@RequestBody DataDictionaryParam dataDictionaryParam) {
        DataDictionary dataDictionary = new DataDictionary();
        return dataDictionaryService.dictionaryList(dataDictionaryParam);
    }

}
