package com.sang.common.snowId;

import com.sang.common.utils.SnowIdUtils;
import io.ebean.config.IdGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SnowIdGenerator implements IdGenerator,Serializable {

    @Override
    public Object nextValue() {
        return SnowIdUtils.uniqueLong();
    }

    @Override
    public String getName() {
        return "SnowIdGenerator";
    }
}
