package com.sang.system.example.ebean.batch;

import com.sang.common.domain.user.entity.User;
import io.ebean.DB;
import io.ebean.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

/**
 * @author hxy
 * @date 2022/2/10 14:20
 **/
@SpringBootTest
class TestEbeanBatchExample {

    /**
     * 未验证
     */
    @Test
    void batchTest() {

        ArrayList<User> users = new ArrayList<>();
        User user = new User();
        users.add(user);

        Transaction transaction = DB.beginTransaction();
        try {
            transaction.setBatchMode(true);
            // 每200条语句刷新（提交）一次缓存区
            transaction.setBatchSize(200);
            // 本事务内查询时先刷新
            transaction.setFlushOnQuery(true);
            // 关闭级联
            transaction.setPersistCascade(false);
            // 关闭Get自动生成的id
            transaction.setGetGeneratedKeys(false);

            users.forEach(User::save);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback(e);
        } finally {
            transaction.end();
        }
    }
}
