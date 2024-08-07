package com.sang.system.service.${domain};

import com.sang.common.domain.${domain}.entity.${model};
import com.sang.common.domain.${domain}.param.${model}Qry;
import io.ebean.PagedList;

import java.util.List;

/**
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * @author ${author}
 * @since ${createDate?string("yyyy-MM-dd HH:mm:ss")}
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