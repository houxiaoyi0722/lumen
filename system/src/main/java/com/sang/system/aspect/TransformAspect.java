package com.sang.system.aspect;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.sang.common.annotation.dictionary.DictionaryTran;
import com.sang.common.annotation.dictionary.TargetField;
import com.sang.common.annotation.dictionary.Transform;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.common.domain.dict.entity.DictionaryItem;
import com.sang.system.adapter.DictionaryAdapter;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xiaoy
 * 数据字典转换切换
 */
@Component
@Aspect
@Slf4j
public class TransformAspect {

    @Resource
    private DictionaryAdapter dictionaryAdapter;

    @Pointcut("@annotation(com.sang.common.annotation.dictionary.Transform)")
    public void transformServer() {
    }

    /**
     * 返回值有三种情况
     * 列表\bean\包装类 (类似公共返回对象)
     * 列表\bean和包装类 (类似公共返回对象)之间区分 是否拆包
     * 拆包目标对象优先使用<code>com.sang.annotation.dictionary.Transform</code>注解上的数据
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("transformServer()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取切入点签名
        Signature signature = joinPoint.getSignature();

        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;

        // 获取被注解标注的方法对象,拿到Transform注解,获取包装类的目标字段
        Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), methodSignature.getParameterTypes());
        Transform annotation = method.getAnnotation(Transform.class);
        // 优先读取targetField指定的字段,如果字段为空,检查返回对象中是否有标记注解,如果有,读取该字段数据
        String targetFieldName = annotation.targetField();

        // 如果未指定,检查目标对象是否指定TargetField注解
        Object result = joinPoint.proceed();

        Class<?> resultClass = result.getClass();

        List<Field> targetFields;
        if (StrUtil.isNotBlank(targetFieldName)) {
            targetFields = List.of(resultClass.getDeclaredField(targetFieldName));
        } else {
            targetFields = Arrays.stream(resultClass.getDeclaredFields()).filter(item -> item.getAnnotation(TargetField.class) != null).collect(Collectors.toList());
        }

        // 筛选有注解标记的字段
        for (Field targetField : targetFields) {
            targetField.setAccessible(true);
            try {
                Optional<Object> fieldVluOpt = Optional.ofNullable(targetField.get(result));

                fieldVluOpt.ifPresent(fieldVlu -> {
                    try {
                        // 类型判断 只支持list或者entity
                        if (List.class.isAssignableFrom(fieldVlu.getClass())) {
                            targetField.set(result, conversionDictionaryMappingList((List) fieldVlu));
                        } else {
                            targetField.set(result, conversionDictionaryMapping(fieldVlu));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("设置目标字段数据出错",e);
                    }
                });
            } catch (IllegalAccessException e) {
                throw new RuntimeException("读取目标字段数据出错",e);
            }
        }

        return result;
    }

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
                        throw new IllegalArgumentException("groupId can not be empty");

                    //字典数据
                    List<Dictionary> fetch = dictionaryAdapter.getDictionaryListByGroupIds(groupIds);

                    try {
                        for (Field field : dictionaryField) {
                            DictionaryTran annotation = field.getAnnotation(DictionaryTran.class);
                            //设置每个对象该field的值
                            for (T ori : oriList) {
                                setTargetFieldValue(ori, fetch, field, annotation.groupId(), annotation.valueTargetField());
                            }
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        log.error("",e);
                    }
                }
            }
        } catch (Exception e) {
            log.error("字典转换错误", e);
            return oriList;
        }
        return oriList;
    }

    private <T> T conversionDictionaryMapping(T ori) {
        Class<?> cls = ori.getClass();
        //待处理字段集合
        List<Field> dictionaryField = getDictionaryFields(cls);
        //需要的字典数据id
        List<String> groupIds = getGroupIds(dictionaryField);

        //字典数据
        List<Dictionary> fetch = dictionaryAdapter.getDictionaryListByGroupIds(groupIds);

        try {
            for (Field field : dictionaryField) {
                DictionaryTran annotation = field.getAnnotation(DictionaryTran.class);
                setTargetFieldValue(ori, fetch, field, annotation.groupId(), annotation.valueTargetField());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("",e);
        }

        return ori;
    }

    /**
     * 获取有Dictionary注解的字段
     *
     * @param cls cls对象
     * @return 有Dictionary注解的字段
     */
    private List<Field> getDictionaryFields(Class<?> cls) {
        return Arrays.stream(cls.getDeclaredFields())
                .filter(obj -> Optional.ofNullable(obj.getAnnotation(DictionaryTran.class))
                        .isPresent()).collect(Collectors.toList());
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
    private <T> void setTargetFieldValue(T ori, List<Dictionary> fetch, Field field, String groupId, String targetField) throws IllegalAccessException, NoSuchFieldException {
        Class<?> cls = ori.getClass();
        field.setAccessible(true);
        //获取字段中的值
        Optional<Object> optional = Optional.ofNullable(field.get(ori));
        //判断是否为空 null不处理
        if (optional.isEmpty())
            return;

        Object target = optional.get();

        Optional<Dictionary> dataDictionary = fetch.stream().filter(t -> groupId.equals(t.getGroupId())).findAny();
        if (dataDictionary.isEmpty()) {
            log.warn("groupId doesn't exist： {}",groupId);
            return;
        }

        List<DictionaryItem> dictionaryItems = dataDictionary.get().getDictionaryItems();
        if (CollectionUtil.isEmpty(dictionaryItems)) {
            log.warn("can't find dict item:groupId= {}",groupId);
            return;
        }

        Object finalTarget = target;
        target = dictionaryItems.stream().filter(dataDictionaryItem -> finalTarget.equals(dataDictionaryItem.getItemKey())).map(DictionaryItem::getItemValue).findAny().orElse(StringConst.EMPTY);

        //将值设置回该字段或目标字段
        Field fieldObj = StringUtils.isEmpty(targetField) ? field : cls.getDeclaredField(targetField);
        fieldObj.setAccessible(true);
        fieldObj.set(ori, target);
    }

    private List<String> getGroupIds(List<Field> dictionaryField) {
        List<String> groupIds = dictionaryField.stream()
                .map(obj -> obj.getAnnotation(DictionaryTran.class).groupId())
                .filter(StrUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        return groupIds;
    }


}
