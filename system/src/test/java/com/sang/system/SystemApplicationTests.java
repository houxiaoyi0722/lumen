package com.sang.system;

import com.sang.system.domain.DataDictionary;
import com.sang.provider.CurrentUser;
import com.sang.snowId.SnowIdGenerator;
import io.ebean.DB;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SystemApplicationTests {

    @Test
    void contextLoads() {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.add(new SnowIdGenerator());
        databaseConfig.setRegister(true);
        databaseConfig.setDefaultServer(true);
        databaseConfig.setCurrentUserProvider(new CurrentUser());
        databaseConfig.loadFromProperties();
        Database database = DatabaseFactory.create(databaseConfig);

        DataDictionary build = DataDictionary.builder().groupKey("11").groupValue("22").build();
        DB.save(build);
        System.out.println(111);
    }

}
