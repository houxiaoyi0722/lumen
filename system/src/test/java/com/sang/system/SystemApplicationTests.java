package com.sang.system;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.common.domain.dict.entity.DictionaryItem;
import io.ebean.DB;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class SystemApplicationTests {

    @Test
    void contextLoads() {
        Dictionary build = Dictionary.builder().groupId("22").build();
        DictionaryItem build1 = DictionaryItem.builder().itemKey("11").itemValue("132").build();
        ArrayList<DictionaryItem> objects = new ArrayList<>();
        objects.add(build1);
        build.setDictionaryItems(objects);

        DB.save(build);
    }

}
