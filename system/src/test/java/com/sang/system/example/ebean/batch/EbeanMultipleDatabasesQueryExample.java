package com.sang.system.example.ebean.batch;

import com.sang.common.domain.dict.entity.query.QDictionary;
import io.ebean.DB;
import io.ebean.Database;
import org.junit.jupiter.api.Test;

/**
 * @author hxy
 * @date 2022/2/16 18:25
 **/
class EbeanMultipleDatabasesQueryExample {
//    @Test
    void ebeanMultipleDatabasesQueryExample() {
        Database db = DB.byName("db");
        // 直接在配置文件里配置数据源即可
        Database other = DB.byName("other");

        int count = new QDictionary(other).findCount();

        int count2 = new QDictionary(db).findCount();

    }
}
