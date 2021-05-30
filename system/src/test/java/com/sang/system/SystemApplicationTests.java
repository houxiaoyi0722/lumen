package com.sang.system;

import com.sang.system.domain.entity.DataDictionary;
import com.sang.system.domain.entity.DataDictionaryItem;
import io.ebean.DB;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class SystemApplicationTests {

    @Test
    void contextLoads() {
        DataDictionary build = DataDictionary.builder().groupKey("11").groupValue("22").build();
        DataDictionaryItem build1 = DataDictionaryItem.builder().itemKey("11").itemValue("132").build();
        ArrayList<DataDictionaryItem> objects = new ArrayList<>();
        objects.add(build1);
        build.setDataDictionaryItems(objects);

        DB.save(build);
    }

}
