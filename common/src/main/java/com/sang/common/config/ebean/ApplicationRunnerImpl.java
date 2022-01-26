//package com.sang.config;
//
//import com.sang.provider.CurrentUser;
//import com.sang.snowId.SnowIdGenerator;
//import io.ebean.DB;
//import io.ebean.Database;
//import io.ebean.DatabaseFactory;
//import io.ebean.config.DatabaseConfig;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Log4j2
//@Component
//public class ApplicationRunnerImpl implements ApplicationRunner {
//
//    @Resource
//    private CurrentUser currentUser;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        DatabaseConfig databaseConfig = new DatabaseConfig();
//        databaseConfig.add(new SnowIdGenerator());
//        databaseConfig.setRegister(true);
//        databaseConfig.setDefaultServer(true);
//        databaseConfig.setCurrentUserProvider(currentUser);
//        databaseConfig.loadFromProperties();
//        Database database = DatabaseFactory.create(databaseConfig);
//        log.info("ebean default database : {}", DB.getDefault().getName());
//    }
//}
