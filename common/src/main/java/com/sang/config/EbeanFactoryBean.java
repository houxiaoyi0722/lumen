//package com.sang.config;
//
//import com.sang.provider.CurrentUser;
//import com.sang.snowId.SnowIdGenerator;
//import io.ebean.Database;
//import io.ebean.DatabaseFactory;
//import io.ebean.config.DatabaseConfig;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Configuration
//public class EbeanFactoryBean implements FactoryBean<Database> {
//
//    @Resource
//    private CurrentUser currentUser;
//
//    @Resource
//    private SnowIdGenerator snowIdGenerator;
//
//    @Override
//    public Database getObject() throws Exception {
//        DatabaseConfig databaseConfig = new DatabaseConfig();
//        databaseConfig.add(snowIdGenerator);
//        databaseConfig.setRegister(false);
//        databaseConfig.setDefaultServer(true);
//        databaseConfig.setCurrentUserProvider(currentUser);
//        databaseConfig.loadFromProperties();
//
////        //是否执行建表SQL
////        config.setDdlRun(true);
////        //是否生成建表SQL
////        config.setDdlGenerate(true);
////        //是否跳过删表SQL
////        config.setDdlCreateOnly(true);
//
//        databaseConfig.setRunMigration(true);
//
//        Database database = DatabaseFactory.create(databaseConfig);
//        return database;
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return Database.class;
//    }
//
//    @Override
//    public boolean isSingleton() {
//        return true;
//    }
//}
