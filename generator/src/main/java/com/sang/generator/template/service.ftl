package com.sang.system.service.${domain};

import com.sang.common.domain.${domain}.entity.${model};
import com.sang.common.domain.${domain}.param.${model}Qry;
import io.ebean.PagedList;

import java.util.List;

/**
* ${comment}
* ${auther}
* ${createDate?string("yyyy-MM-dd HH:mm:ss")}
*/
public interface ${model}Service {

    PagedList<${model}> ${model}List(${model}Qry qry);

    ${model} findOne(Long id);
<#-- todo 小驼峰-->
    void save(${model} ${model});

    void saveAll(List<${model}> ${model});

    void insert(${model} ${model});

    void update(${model} ${model});

    void delete(${model} ${model});

    void deleteAll(List<${model}> ${model}s);
}