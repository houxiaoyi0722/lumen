package com.sang.common.domain.${domain}.entity.finder;

import com.sang.common.domain.${domain}.entity.${domain};
import io.ebean.Finder;
import lombok.Builder;

@Builder
public class ${domain}Finder extends Finder<Long, ${domain}> {

    /**
    * Construct using the default EbeanServer.
    */
    public ${domain}Finder() {
        super(${domain}.class);
    }

}

