package com.sang.common.domain.dict.repo;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.common.domain.dict.entity.query.QDictionary;
import com.sang.common.domain.dict.param.DataDictionaryQry;
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DictionaryRepository extends BeanRepository<Long, Dictionary> {

    protected DictionaryRepository(Database server) {
        super(Dictionary.class, server);
    }

    public List<Dictionary> getDictionaryListByGroupIds(List<String> groupIds) {
        return new QDictionary().groupId.in(groupIds).deleted.isFalse().findList();
    }

    public PagedList<Dictionary> getDictionaryList(DataDictionaryQry dataDictionaryQry) {
        QDictionary dataDictionary = QDictionary.alias();

        return new QDictionary()
                .select()
                .setFirstRow(dataDictionaryQry.getStartPosition())
                .setMaxRows(dataDictionaryQry.getEndPosition())
                .orderBy().whenCreated.desc()
                .findPagedList();
    }
}
