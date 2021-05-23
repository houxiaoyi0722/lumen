package com.sang.system;

import com.sang.system.domain.DataDictionary;
import io.ebean.DB;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SystemApplicationTests {

    @Test
    void contextLoads() {
        DataDictionary build = DataDictionary.builder().groupKey("11").groupValue("22").build();
        DB.save(build);
        System.out.println(111);
    }

}
