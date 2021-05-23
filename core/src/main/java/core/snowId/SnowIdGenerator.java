package core.snowId;

import core.utils.SnowIdUtils;
import io.ebean.annotation.EbeanComponent;
import io.ebean.config.IdGenerator;

import java.io.Serializable;

@EbeanComponent
public class SnowIdGenerator implements IdGenerator,Serializable {

    @Override
    public Object nextValue() {
        return SnowIdUtils.uniqueLong();
    }

    @Override
    public String getName() {
//        return this.getClass().getSimpleName();
        return "SnowIdGenerator";
    }
}
