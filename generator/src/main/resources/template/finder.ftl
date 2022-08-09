package com.sang.common.domain.${domain?lower_case}.entity.finder;

import com.sang.common.domain.${domain?lower_case}.entity.${model};
import io.ebean.Finder;
import lombok.Builder;

/**
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * ${author} ${createDate?string("yyyy-MM-dd HH:mm:ss")}
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

