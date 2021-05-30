package com.sang.system.domain.aggregate;

import com.sang.system.domain.repo.DataDictionaryRepository;
import io.ebean.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataDictionaryAggregate {

    @Autowired
    private DataDictionaryRepository dataDictionaryRepository;

    @Autowired
    private Database db;

}
