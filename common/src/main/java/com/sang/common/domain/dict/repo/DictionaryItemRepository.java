package com.sang.common.domain.dict.repo;

import com.sang.common.domain.dict.entity.DictionaryItem;
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

@Repository
public class DictionaryItemRepository extends BeanRepository<Long, DictionaryItem> {
    protected DictionaryItemRepository(Database server) {
        super(DictionaryItem.class, server);
    }

}
