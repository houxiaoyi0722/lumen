package com.sang.config;

import com.sang.provider.CurrentUser;
import com.sang.snowId.SnowIdGenerator;
import io.ebean.DB;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;

@Configuration
@Log4j2
public class EbeanDefaultDatabaseConfig {


    @Resource
    private CurrentUser currentUser;

    @Resource
    private SnowIdGenerator snowIdGenerator;

    @Bean
    @Primary
    public Database database() {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.add(snowIdGenerator);
        databaseConfig.setRegister(true);
        databaseConfig.setDefaultServer(true);
        databaseConfig.setCurrentUserProvider(currentUser);
        databaseConfig.loadFromProperties();
        databaseConfig.setDefaultServer(true);
//        databaseConfig.setDdlCreateOnly(true);

//        databaseConfig.setRunMigration(true);

        Database database = DatabaseFactory.create(databaseConfig);
        log.info("ebean default database : {}", DB.getDefault().getName());
        return database;
    }
}
