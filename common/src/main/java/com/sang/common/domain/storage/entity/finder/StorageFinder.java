package com.sang.common.domain.storage.entity.finder;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.common.domain.storage.entity.Storage;
import io.ebean.Finder;
import lombok.Builder;

@Builder
public class StorageFinder extends Finder<Long, Storage> {

    /**
     * Construct using the default EbeanServer.
     */
    public StorageFinder() {
        super(Storage.class);
    }

}
