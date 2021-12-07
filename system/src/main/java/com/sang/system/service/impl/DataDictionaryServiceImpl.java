package com.sang.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.sang.annotation.dictionary.Dictionary;
import com.sang.constants.StringConst;
import com.sang.system.domain.entity.DataDictionary;
import com.sang.system.domain.entity.DataDictionaryItem;
import com.sang.system.domain.repo.DataDictionaryRepository;
import com.sang.system.param.DataDictionaryParam;
import com.sang.system.service.DataDictionaryService;
import io.ebean.PagedList;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 数据字典
 * @author xiaoy
 */
@Log4j2
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

    @Resource
    private DataDictionaryRepository dataDictionaryRepository;

    /**
     * 转换list中的字典值 , 需在entity需要转换的字段上添加<tt>Dictionary</tt>注解
     * @param oriList list
     * @return 返回转换后的对象
     */
    @Override
    public <T> List<T> conversionDictionaryMappingList(List<T> oriList) {
        try {
            if (CollectionUtil.isNotEmpty(oriList)) {
                //待处理字段
                List<Field> dictionaryField = getDictionaryFields(oriList.get(0).getClass());
                //无注解原值返回
                if (CollectionUtil.isNotEmpty(dictionaryField)) {
                    //需要的字典数据id
                    List<String> groupIds = getGroupIds(dictionaryField);

                    if (CollectionUtil.isEmpty(groupIds))
                        throw new IllegalArgumentException("groupId 不能为空");

                    //字典数据
                    List<DataDictionary> fetch = dataDictionaryRepository.getDictionaryListByGroupIds(groupIds);

                    try {
                        for (Field field : dictionaryField) {
                            Dictionary annotation = field.getAnnotation(Dictionary.class);
                            //设置每个对象该field的值
                            for (T ori : oriList) {
                                setTargetFieldValue(ori, fetch, field, annotation.groupId(), annotation.targetField());
                            }
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        log.error(e);
                    }
                }
            }
        } catch (Exception e) {
            log.error("字典转换错误", e);
            return oriList;
        }
        return oriList;
    }


    @Override
    public <T> T conversionDictionaryMapping(T ori) {
        Class<?> cls = ori.getClass();
        //待处理字段集合
        List<Field> dictionaryField = getDictionaryFields(cls);
        //需要的字典数据id
        List<String> groupIds = getGroupIds(dictionaryField);

        //字典数据
        List<DataDictionary> fetch = dataDictionaryRepository.getDictionaryListByGroupIds(groupIds);

        try {
            for (Field field : dictionaryField) {
                Dictionary annotation = field.getAnnotation(Dictionary.class);
                setTargetFieldValue(ori, fetch, field, annotation.groupId(), annotation.targetField());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error(e);
        }

        return ori;
    }

    @Override
    public PagedList<DataDictionary> dictionaryList(DataDictionaryParam dataDictionaryParam) {
        return dataDictionaryRepository.getDictionaryList(dataDictionaryParam);
    }

    @Override
    public DataDictionary findOne(String id) {
        return null;
    }

    @Override
    public DataDictionary save(DataDictionary dataDictionary) {
        return null;
    }

    @Override
    public DataDictionary update(DataDictionary dataDictionary) {
        return null;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }

    /**
     * 设置目标字段的值
     *
     * @param ori         目标对象
     * @param fetch       groupIds 对应的item数据
     * @param field       要设置值的字段
     * @param groupId     该字段的groupId
     * @param targetField 目标字段
     * @throws NoSuchFieldException targetField不存在时
     */
    private <T> void setTargetFieldValue(T ori, List<DataDictionary> fetch, Field field, String groupId, String targetField) throws IllegalAccessException, NoSuchFieldException {
        Class<?> cls = ori.getClass();
        field.setAccessible(true);
        //获取字段中的值
        Optional<Object> optional = Optional.ofNullable(field.get(ori));
        //判断是否为空 null不处理
        if (optional.isEmpty())
            return;

        Object target = optional.get();

        Optional<DataDictionary> dataDictionary = fetch.stream().filter(t -> groupId.equals(t.getGroupId())).findAny();
        if (dataDictionary.isEmpty()) {
            log.warn("groupId不存在： {}",groupId);
            return;
        }

        List<DataDictionaryItem> dataDictionaryItems = dataDictionary.get().getDataDictionaryItems();
        if (CollectionUtil.isEmpty(dataDictionaryItems)) {
            log.warn("字典列表为空:groupId= {}",groupId);
            return;
        }

        Object finalTarget = target;
        target = dataDictionaryItems.stream().filter(dataDictionaryItem -> finalTarget.equals(dataDictionaryItem.getItemKey())).map(DataDictionaryItem::getItemValue).findAny().orElse(StringConst.EMPTY);

        //将值设置回该字段或目标字段
        Field fieldObj = StringUtils.isEmpty(targetField) ? field : cls.getDeclaredField(targetField);
        fieldObj.setAccessible(true);
        fieldObj.set(ori, target);
    }

    private List<String> getGroupIds(List<Field> dictionaryField) {
        List<String> groupIds = dictionaryField.stream()
                .map(obj -> obj.getAnnotation(Dictionary.class).groupId())
                .filter(StrUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        return groupIds;
    }

    /**
     * 获取有Dictionary注解的字段
     *
     * @param cls cls对象
     * @return 有Dictionary注解的字段
     */
    private List<Field> getDictionaryFields(Class<?> cls) {
        return Arrays.stream(cls.getDeclaredFields())
                .filter(obj -> Optional.ofNullable(obj.getAnnotation(Dictionary.class))
                        .isPresent()).collect(Collectors.toList());
    }
}
