package com.sang.system.service.dict.impl;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.system.domain.dict.repo.DictionaryRepository;
import com.sang.system.param.dict.DataDictionaryParam;
import com.sang.system.service.dict.DictionaryService;
import io.ebean.PagedList;
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
// 提取公共代码，使得Dictionary数据源可自定义
@ConditionalOnMissingBean(DictionaryService.class)
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryRepository dictionaryRepository;

    /**
     * 配合提供数据 转换list中的字典值 , 需在entity需要转换的字段上添加<tt>Dictionary</tt>注解
     *
     * @param groupIds list
     * @return 返回转换后的对象
     */
    @Override
    public List<Dictionary> getDictionaryListByGroupIds(List<String> groupIds) {
        return dictionaryRepository.getDictionaryListByGroupIds(groupIds);
    }

    @Override
    public PagedList<Dictionary> dictionaryList(DataDictionaryParam dataDictionaryParam) {
        return dictionaryRepository.getDictionaryList(dataDictionaryParam);
    }

    @Override
    public Dictionary findOne(Long id) {
        return dictionaryRepository.findById(id);
    }

    @Override
    public void save(Dictionary dictionary) {
        dictionaryRepository.save(dictionary);
    }

    @Override
    public void saveAll(List<Dictionary> dictionaries) {
        dictionaryRepository.saveAll(dictionaries);
    }

    @Override
    public void insert(Dictionary dictionary) {
        dictionaryRepository.insert(dictionary);
    }

    @Override
    public void update(Dictionary dictionary) {
        dictionaryRepository.update(dictionary);
    }

    @Override
    public void delete(Long id) {
        dictionaryRepository.deleteById(id);
    }

}
