package com.sang.system.service.dict;

import com.sang.common.domain.dict.entity.DictionaryItem;

import java.util.List;

public interface DictionaryItemService {

    DictionaryItem findOne(Long id);

    void save(DictionaryItem DictionaryItem);

    void saveAll(List<DictionaryItem> dictionaries);

    void insert(DictionaryItem DictionaryItem);

    void update(DictionaryItem DictionaryItem);

    void delete(DictionaryItem DictionaryItem);

    void deleteAll(List<DictionaryItem> dictionaryItems);
}
