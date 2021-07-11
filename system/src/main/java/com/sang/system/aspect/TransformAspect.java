package com.sang.system.aspect;

import com.sang.system.service.DataDictionaryService;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
        Object result = joinPoint.proceed();
        // 类型判断 只支持list或者entity
        Class<?> aClass = result.getClass();
        if (List.class.isAssignableFrom(aClass)) {
            List cast = (List) result;
            result = dataDictionaryService.conversionDictionaryMappingList(cast);
        } else {
            result = dataDictionaryService.conversionDictionaryMapping(result);
        }

        return result;
    }

}
