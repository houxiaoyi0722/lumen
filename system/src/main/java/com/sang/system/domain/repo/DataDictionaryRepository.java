package com.sang.system.domain.repo;

import cn.hutool.db.PageResult;
import com.sang.system.domain.entity.DataDictionary;
import com.sang.system.domain.entity.query.QDataDictionary;
import com.sang.system.param.DataDictionaryParam;
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataDictionaryRepository extends BeanRepository<Long, DataDictionary> {

    protected DataDictionaryRepository(Database server) {
        super(DataDictionary.class, server);
    }

    public List<DataDictionary> getDictionaryListByGroupIds(List<String> groupIds) {
        return new QDataDictionary().groupId.in(groupIds).deleted.isFalse().findList();
    }

    public PagedList<DataDictionary> getDictionaryList(DataDictionaryParam dataDictionaryParam) {
        QDataDictionary dataDictionary = QDataDictionary.alias();

        return new QDataDictionary()
                .select(dataDictionary.groupId,
                        dataDictionary.groupName,
                        dataDictionary.comment)
                .deleted.isFalse()
                .setFirstRow(dataDictionaryParam.getStartPosition())
                .setMaxRows(dataDictionaryParam.getEndPosition())
                .orderBy().whenCreated.desc()
                .findPagedList();
    }
}
