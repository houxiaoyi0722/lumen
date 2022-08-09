package com.sang.common.domain.${model?lower_case}.param;

import cn.hutool.db.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询对象
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * ${author} ${createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
@Getter
@Setter
@AllArgsConstructor
public class ${model}Qry extends Page {

}
