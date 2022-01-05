package com.sang.system.adapter;

import com.sang.common.domain.dict.entity.Dictionary;

import java.util.List;

/**
 * @author hxy
 * @date 2022/1/5 14:34
 **/
public interface DictionaryAdapter {

    List<Dictionary> getDictionaryListByGroupIds(List<String> groupIds);

}
