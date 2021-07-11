package com.sang.system.service;

import java.util.List;

public interface DataDictionaryService {

    <T> List<T> conversionDictionaryMappingList(List<T> oriList);

    <T> T conversionDictionaryMapping(T ori);
}
