package com.sang.config;

import com.sang.provider.CurrentUser;
import com.sang.snowId.SnowIdGenerator;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EbeanFactoryBean implements FactoryBean<Database> {

    @Resource
    private CurrentUser currentUser;

    @Resource
    private SnowIdGenerator snowIdGenerator;

    @Override
    public Database getObject() throws Exception {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.add(snowIdGenerator);
        databaseConfig.setRegister(false);
        databaseConfig.setDefaultServer(true);
        databaseConfig.setCurrentUserProvider(currentUser);
        databaseConfig.loadFromProperties();
        Database database = DatabaseFactory.create(databaseConfig);
        return database;
    }

    @Override
    public Class<?> getObjectType() {
        return Database.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
