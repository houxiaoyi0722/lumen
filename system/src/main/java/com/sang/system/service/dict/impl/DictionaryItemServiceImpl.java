package com.sang.system.service.dict.impl;

import com.sang.common.domain.dict.entity.DictionaryItem;
import com.sang.common.domain.dict.repo.DictionaryItemRepository;
import com.sang.system.service.dict.DictionaryItemService;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典item
 * @author xiaoy
 */
@Slf4j
@Service
public class DictionaryItemServiceImpl implements DictionaryItemService {

    @Resource
    private DictionaryItemRepository dictionaryItemRepository;

    @Override
    public DictionaryItem findOne(Long id) {
        return dictionaryItemRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(DictionaryItem DictionaryItem) {
        DictionaryItem.save();
    }

    @Override
    @Transactional
    public void insert(DictionaryItem DictionaryItem) {
        DictionaryItem.insert();
    }

    @Override
    @Transactional
    public void update(DictionaryItem DictionaryItem) {
        DictionaryItem.update();
    }

    @Override
    @Transactional
    public void delete(DictionaryItem DictionaryItem) {
        dictionaryItemRepository.delete(DictionaryItem);
    }

    @Override
    @Transactional
    public void saveAll(List<DictionaryItem> dictionaries) {
        dictionaryItemRepository.saveAll(dictionaries);
    }

    @Override
    @Transactional
    public void deleteAll(List<DictionaryItem> dictionaries) {
        dictionaryItemRepository.deleteAll(dictionaries);
    }

}
