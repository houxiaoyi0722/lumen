package com.sang.system.controller;

import com.sang.annotation.dictionary.Transform;
import com.sang.domain.system.entity.Test;
import com.sang.system.service.DataDictionaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/lumen/matedata")
public class DataDictionaryController {

    @Resource
    private DataDictionaryService dataDictionaryService;

    @Transform
    @GetMapping("/test")
    public Test test() {
        return new Test();
    }

}
