package com.sang.system.service;

import cn.hutool.db.PageResult;
import com.sang.system.domain.entity.DataDictionary;
import com.sang.system.param.DataDictionaryParam;
import io.ebean.PagedList;

import java.util.List;

public interface DataDictionaryService {

    <T> List<T> conversionDictionaryMappingList(List<T> oriList);

    <T> T conversionDictionaryMapping(T ori);

    PagedList<DataDictionary> dictionaryList(DataDictionaryParam dataDictionaryParam);

}
