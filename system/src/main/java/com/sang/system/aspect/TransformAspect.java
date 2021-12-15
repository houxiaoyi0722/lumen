package com.sang.system.aspect;

import com.sang.annotation.dictionary.Transform;
import com.sang.system.service.DataDictionaryService;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

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

    @Around("transformServer()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();

        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;

        Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), methodSignature.getParameterTypes());
        Transform annotation = method.getAnnotation(Transform.class);

        String s = annotation.targetField();

        Object result = joinPoint.proceed();
        // 类型判断 只支持list或者entity
        Class<?> aClass = result.getClass();
        // todo 可通过重写 接口方法 切换实现逻辑
        if (List.class.isAssignableFrom(aClass)) {
            List cast = (List) result;
            result = dataDictionaryService.conversionDictionaryMappingList(cast);
        } else {
            result = dataDictionaryService.conversionDictionaryMapping(result);
        }

        return result;
    }

}
