package com.sang.system.service.dict.impl;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.system.domain.dict.repo.DictionaryRepository;
import com.sang.system.param.dict.DataDictionaryParam;
import com.sang.system.service.dict.DictionaryService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典
 * @author xiaoy
 */
@Log4j2
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryRepository dictionaryRepository;

    @Override
    public PagedList<Dictionary> dictionaryList(DataDictionaryParam dataDictionaryParam) {
        return dictionaryRepository.getDictionaryList(dataDictionaryParam);
    }

    @Override
    public Dictionary findOne(Long id) {
        return dictionaryRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(Dictionary dictionary) {
        dictionary.save();
    }

    @Override
    @Transactional
    public void saveAll(List<Dictionary> dictionaries) {
        dictionaryRepository.saveAll(dictionaries);
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
    public void delete(Long id) {
        dictionaryRepository.deleteById(id);
    }

}
