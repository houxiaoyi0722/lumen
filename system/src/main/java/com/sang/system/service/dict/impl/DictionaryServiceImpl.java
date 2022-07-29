package com.sang.system.service.dict.impl;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.common.domain.dict.param.DataDictionaryQry;
import com.sang.common.domain.dict.repo.DictionaryRepository;
import com.sang.system.service.dict.DictionaryService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典
 * @author xiaoy
 */
@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryRepository dictionaryRepository;

    @Override
    public PagedList<Dictionary> dictionaryList(DataDictionaryQry dataDictionaryQry) {
        return dictionaryRepository.getDictionaryList(dataDictionaryQry);
    }

    @Override
    public Dictionary findOne(Long id) {
        return dictionaryRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(Dictionary dictionary) {
        dictionary.save();
    }

    @Override
    @Transactional
    public void insert(Dictionary dictionary) {
        dictionary.insert();
    }

    @Override
    @Transactional
    public void update(Dictionary dictionary) {
        dictionary.update();
    }

    @Override
    @Transactional
    public void delete(Dictionary dictionary) {
        dictionaryRepository.delete(dictionary);
    }

    @Override
    @Transactional
    public void saveAll(List<Dictionary> dictionaries) {
        dictionaryRepository.saveAll(dictionaries);
    }

    @Override
    @Transactional
    public void deleteAll(List<Dictionary> dictionaries) {
        dictionaryRepository.deleteAll(dictionaries);
    }

}
