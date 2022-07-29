package com.sang.common.domain.storage.repo;

import com.sang.common.domain.storage.entity.Storage;
import com.sang.common.domain.storage.entity.query.QStorage;
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

/**
 * @author hxy
 * @date 2022/07/01
 **/
@Repository
public class StorageRepository extends BeanRepository<Long, Storage> {

    protected StorageRepository(Database server) {
        super(Storage.class,server);
    }


    public Storage findByBusiness(String businessCode, String businessType) {
        return new QStorage().select().businessCode.eq(businessCode).businessType.eq(businessType).findOne();
    }
}
