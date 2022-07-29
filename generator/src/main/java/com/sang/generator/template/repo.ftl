package com.sang.common.domain.${domain}.repo;

import com.sang.common.domain.${domain}.entity.${domain};
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ${domain}Repository extends BeanRepository<Long, ${domain}> {

    protected ${domain}Repository(Database server) {
        super(${domain}.class, server);
    }


}
