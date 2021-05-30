package com.sang.system.domain.repo;

import com.sang.system.domain.entity.DataDictionary;
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

@Repository
public class DataDictionaryRepository extends BeanRepository<Long, DataDictionary> {
    protected DataDictionaryRepository(Database server) {
        super(DataDictionary.class, server);
    }
}
