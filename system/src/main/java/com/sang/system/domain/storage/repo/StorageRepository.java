package com.sang.system.domain.storage.repo;

import com.sang.common.domain.storage.entity.Storage;
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

@Repository
public class StorageRepository extends BeanRepository<Long, Storage> {

    protected StorageRepository(Database server) {
        super(Storage.class,server);
    }








}
