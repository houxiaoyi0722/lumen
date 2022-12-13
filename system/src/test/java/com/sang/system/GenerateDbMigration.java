package com.sang.system;

import io.ebean.annotation.Platform;
import io.ebean.dbmigration.DbMigration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Generate the DB Migration.
 */
@SpringBootTest
public class GenerateDbMigration {

  @Test
  public void generate() throws Exception {

    DbMigration dbMigration = DbMigration.create();
    dbMigration.setPlatform(Platform.MYSQL);
//    dbMigration.setPathToResources("foo/bar");

//    dbMigration.setAddForeignKeySkipCheck(true);
//    dbMigration.setLockTimeout(10);

    // generate the migration ddl and xml
//    dbMigration.setGeneratePendingDrop("1.3");
    // 运行migration更新版本需要设置        databaseConfig.setRunMigration(false);
//    dbMigration.setStrictMode(false);
    dbMigration.setVersion("1.1");
    dbMigration.generateMigration();
  }
}
