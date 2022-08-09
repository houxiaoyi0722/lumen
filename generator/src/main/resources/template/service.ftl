package com.sang.system.service.${domain?lower_case};

import com.sang.common.domain.${domain?lower_case}.entity.${model};
import com.sang.common.domain.${domain?lower_case}.param.${model}Qry;
import io.ebean.PagedList;

import java.util.List;

/**
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * ${author} ${createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
public interface ${model}Service {

    PagedList<${model}> ${model?lower_case}List(${model}Qry qry);

    ${model} findOne(Long id);

    void save(${model} ${model?lower_case});

    void saveAll(List<${model}> ${model?lower_case}s);

    void insert(${model} ${model?lower_case});

    void update(${model} ${model?lower_case});

    void delete(${model} ${model?lower_case});

    void deleteAll(List<${model}> ${model?lower_case}s);
}