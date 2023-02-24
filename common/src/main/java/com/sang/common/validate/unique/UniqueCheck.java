package com.sang.common.validate.unique;

/**
 * 数据唯一校验函数式接口
 * @param <T>
 */
@FunctionalInterface
public interface UniqueCheck<T> {
    T query();

}
