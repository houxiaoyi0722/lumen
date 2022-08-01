package com.sang.common.domain.${domain}.repo;

import com.sang.common.domain.${domain}.entity.${model};
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${fileComment}
 * ${author}
 * ${createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
@Repository
public class ${model}Repository extends BeanRepository<Long, ${model}> {

    protected ${model}Repository(Database server) {
        super(${model}.class, server);
    }

    public PagedList<${model}> getPage(${model}Qry qry) {
        Q${model} alice = Q${model}.alias();

        return new Q${model}()
            .select()
            .setFirstRow(qry.getStartPosition())
            .setMaxRows(qry.getEndPosition())
            .orderBy().whenCreated.desc()
            .findPagedList();
    }


}
