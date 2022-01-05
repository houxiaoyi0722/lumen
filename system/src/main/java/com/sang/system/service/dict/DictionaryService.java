package com.sang.system.service.dict;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.system.domain.dict.param.DataDictionaryParam;
import io.ebean.PagedList;

import java.util.List;

public interface DictionaryService {

    PagedList<Dictionary> dictionaryList(DataDictionaryParam dataDictionaryParam);

    Dictionary findOne(Long id);

    void save(Dictionary dictionary);

    void saveAll(List<Dictionary> dictionaries);

    void insert(Dictionary dictionary);

    void update(Dictionary dictionary);

    void delete(Long id);
}
