package com.sang.common.domain.dict.repo;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.common.domain.dict.entity.query.QDictionary;
import com.sang.common.domain.dict.param.DictionaryQry;
import io.ebean.BeanRepository;
import io.ebean.DB;
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

    public PagedList<Dictionary> getDictionaryList(DictionaryQry dictionaryQry) {
        QDictionary dataDictionary = QDictionary.alias();

        return new QDictionary()
                .select(dataDictionary.groupId,dataDictionary.groupName,dataDictionary.comment)
                .setFirstRow(dictionaryQry.getStartPosition())
                .setMaxRows(dictionaryQry.getEndPosition())
                .orderBy().whenCreated.desc()
                .findPagedList();
    }
}
