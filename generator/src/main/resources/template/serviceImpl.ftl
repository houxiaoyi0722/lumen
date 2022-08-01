package com.sang.system.service.${domain}.impl;

import com.sang.common.domain.${domain}.entity.${model};
import com.sang.common.domain.${domain}.param.${model}Qry;
import com.sang.common.domain.${domain}.repo.${model}Repository;
import com.sang.system.service.${domain}.${model}Service;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${fileComment}
 * ${author}
 * ${createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
@Slf4j
@Service
public class ${model}ServiceImpl implements ${model}Service {

    @Resource
    private ${model}Repository repository;

    @Override
    public PagedList<${model}> ${model?lower_case}List(${model}Qry qry) {
        return repository.getPage(qry);
    }

    @Override
    public ${model} findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(${model} ${model?lower_case}) {
        ${model?lower_case}.save();
    }

    @Override
    @Transactional
    public void insert(${model} ${model?lower_case}) {
        ${model?lower_case}.insert();
    }

    @Override
    @Transactional
    public void update(${model} ${model?lower_case}) {
        ${model?lower_case}.update();
    }

    @Override
    @Transactional
    public void delete(${model} ${model?lower_case}) {
        repository.delete(${model?lower_case});
    }

    @Override
    @Transactional
    public void saveAll(List<${model}> ${model}s) {
        repository.saveAll(${model}s);
    }

    @Override
    @Transactional
    public void deleteAll(List<${model}> ${model?lower_case}s) {
        repository.deleteAll(${model?lower_case}s);
    }
}