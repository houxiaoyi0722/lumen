package com.sang.system.service.dict;

import com.sang.system.domain.dict.entity.Dictionary;
import com.sang.system.param.dict.DataDictionaryParam;
import io.ebean.PagedList;

import java.util.List;

public interface DictionaryService {

    <T> List<T> conversionDictionaryMappingList(List<T> oriList);

    <T> T conversionDictionaryMapping(T ori);

    PagedList<Dictionary> dictionaryList(DataDictionaryParam dataDictionaryParam);

    Dictionary findOne(String id);

    Dictionary save(Dictionary dictionary);

    Dictionary update(Dictionary dictionary);

    Boolean delete(String id);
}
