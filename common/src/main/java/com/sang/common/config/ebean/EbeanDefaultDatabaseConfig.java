package com.sang.common.config.ebean;

import com.sang.common.provider.CurrentUser;
import com.sang.common.snowId.SnowIdGenerator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.ebean.DB;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;

@Configuration
@Slf4j
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
//        databaseConfig.setDdlCreateOnly(true);
        // 此设置需要先运行GenerateDbMigration生成ddl文件
        databaseConfig.setDdlGenerate(true);
        databaseConfig.setRunMigration(false);
        databaseConfig.setDdlRun(false);
        databaseConfig.setDataSource(dataSourceConfig());
//        databaseConfig.setDataSourceConfig(dataSourceConfig());
        databaseConfig.setDdlInitSql("initData.sql");
        Database database = DatabaseFactory.create(databaseConfig);
        log.info("ebean default database : {}", DB.getDefault().getName());
        return database;
    }

    @Bean(name = "hikariDataSource")
    @QuartzDataSource
    public HikariDataSource dataSourceConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(ebeanDataSourceConfig.getUrl());
        hikariConfig.setUsername(ebeanDataSourceConfig.getUsername());
        hikariConfig.setPassword(ebeanDataSourceConfig.getPassword());
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setPoolName("HikariCP");
        hikariConfig.setIdleTimeout(ebeanDataSourceConfig.getIdleTimeout());
        hikariConfig.setMaximumPoolSize(ebeanDataSourceConfig.getMaxPoolSize());
        hikariConfig.setMaxLifetime(ebeanDataSourceConfig.getMaxLifetime());
        hikariConfig.setConnectionTimeout(ebeanDataSourceConfig.getConnectionTimeout());
        hikariConfig.setDriverClassName(ebeanDataSourceConfig.getDriverClassName());

        return new HikariDataSource(hikariConfig);
    }
}
