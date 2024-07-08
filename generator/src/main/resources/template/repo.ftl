package com.sang.common.domain.${domain}.repo;

import com.sang.common.domain.${domain}.entity.${model};
import com.sang.common.domain.${domain}.param.${model}Qry;
import com.sang.common.domain.${domain}.entity.query.Q${model};
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.springframework.stereotype.Repository;

/**
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * @author ${author}
 * @since ${createDate?string("yyyy-MM-dd HH:mm:ss")}
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
