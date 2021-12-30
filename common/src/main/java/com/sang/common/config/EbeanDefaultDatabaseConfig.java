package com.sang.common.config;

import com.sang.common.snowId.SnowIdGenerator;
import com.sang.common.provider.CurrentUser;
import io.ebean.DB;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
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

    @Resource
    private EbeanDataSourceConfig ebeanDataSourceConfig;

    @Bean
    @Primary
    public Database database() {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.add(snowIdGenerator);
        databaseConfig.setRegister(true);
        databaseConfig.setDefaultServer(true);
        databaseConfig.setCurrentUserProvider(currentUser);
        // 从application.yaml中读取不可更改
        databaseConfig.loadFromProperties();
        databaseConfig.setDefaultServer(true);
//        databaseConfig.setDdlCreateOnly(true);
//        databaseConfig.setRunMigration(true);
        databaseConfig.setDataSourceConfig(dataSourceConfig());

        Database database = DatabaseFactory.create(databaseConfig);
        log.info("ebean default database : {}", DB.getDefault().getName());
        return database;
    }

    @Bean
    public DataSourceConfig dataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername(ebeanDataSourceConfig.getUsername());
        dataSourceConfig.setPassword(ebeanDataSourceConfig.getPassword());
        dataSourceConfig.setUrl(ebeanDataSourceConfig.getUrl());
        return dataSourceConfig;
    }
}
