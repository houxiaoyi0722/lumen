package com.sang.system.adapter.impl;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.system.adapter.DictionaryAdapter;
import com.sang.system.domain.dict.repo.DictionaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典数据源适配器
 * @author hxy
 * @date 2022/1/5 14:39
 **/
@Component
@Slf4j
// 提取公共代码，使得Dictionary数据源可自定义
//@ConditionalOnMissingBean(DictionaryAdapter.class)
public class DictionaryAdapterImpl implements DictionaryAdapter {

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
}
