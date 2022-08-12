package com.sang.common.constants;

import java.util.List;

/**
 * 可生成 Int 数组的接口
 */
public interface IntArrayValuable {

    /**
     * @return int 数组
     */
    List<Integer> array(String parentCode);

}
