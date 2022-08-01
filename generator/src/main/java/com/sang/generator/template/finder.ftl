package com.sang.common.domain.${domain}.entity.finder;

import com.sang.common.domain.${domain}.entity.${domain};
import io.ebean.Finder;
import lombok.Builder;

/**
* ${comment}
* ${auther}
* ${createDate?string("yyyy-MM-dd HH:mm:ss")}
*/
@Builder
public class ${model}Finder extends Finder<Long, ${model}> {

    /**
    * Construct using the default EbeanServer.
    */
    public ${model}Finder() {
        super(${model}.class);
    }

}

