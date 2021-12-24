package com.sang.system.service.dict;

import com.sang.system.domain.dict.entity.DataDictionary;
import com.sang.system.param.dict.DataDictionaryParam;
import io.ebean.PagedList;

import java.util.List;

public interface DataDictionaryService {

    <T> List<T> conversionDictionaryMappingList(List<T> oriList);

    <T> T conversionDictionaryMapping(T ori);

    PagedList<DataDictionary> dictionaryList(DataDictionaryParam dataDictionaryParam);

    DataDictionary findOne(String id);

    DataDictionary save(DataDictionary dataDictionary);

    DataDictionary update(DataDictionary dataDictionary);

    Boolean delete(String id);
}
