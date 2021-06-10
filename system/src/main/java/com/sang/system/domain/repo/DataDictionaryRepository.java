package com.sang.system.domain.repo;

import com.sang.system.domain.entity.DataDictionary;
import com.sang.system.domain.entity.DataDictionaryItem;
import com.sang.system.domain.entity.query.QDataDictionary;
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataDictionaryRepository extends BeanRepository<Long, DataDictionary> {

    protected DataDictionaryRepository(Database server) {
        super(DataDictionary.class, server);
    }

    public List<DataDictionary> getDictionaryListByGroupIds(List<String> groupIds) {
        return new QDataDictionary().groupId.in(groupIds).deleted.eq(false).findList();
    }
}
