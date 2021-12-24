package com.sang.system.aspect;

import cn.hutool.core.util.StrUtil;
import com.sang.annotation.dictionary.TargetField;
import com.sang.annotation.dictionary.Transform;
import com.sang.system.service.dict.DataDictionaryService;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
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
@Log4j2
public class TransformAspect {

    @Resource
    private DataDictionaryService dataDictionaryService;

    @Pointcut("@annotation(com.sang.annotation.dictionary.Transform)")
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
                            targetField.set(result,dataDictionaryService.conversionDictionaryMappingList((List) fieldVlu));
                        } else {
                            targetField.set(result,dataDictionaryService.conversionDictionaryMapping(fieldVlu));
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

}
