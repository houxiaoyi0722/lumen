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
* ${comment}
* ${auther}
* ${createDate?string("yyyy-MM-dd HH:mm:ss")}
*/
@Slf4j
@Service
public class ${model}ServiceImpl implements ${model}Service {

    @Resource
    private ${model}Repository repository;

    @Override
    public PagedList<${model}> ${model}${"domain"?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case}List(${model}Qry qry) {
        return repository.getPage(qry);
    }

    @Override
    public ${model} findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(${model} ${model}) {
        ${model}.save();
    }

    @Override
    @Transactional
    public void insert(${model} ${model}) {
        ${model}.insert();
    }

    @Override
    @Transactional
    public void update(${model} ${model}) {
        ${model}.update();
    }

    @Override
    @Transactional
    public void delete(${model} ${model}) {
        repository.delete(${model});
    }

    @Override
    @Transactional
    public void saveAll(List<${model}> ${model}s) {
        repository.saveAll(${model}s);
    }

    @Override
    @Transactional
    public void deleteAll(List<${model}> ${model}s) {
        repository.deleteAll(${model}s);
    }
}