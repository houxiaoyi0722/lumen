package com.sang.system.example.ebean.batch;

import com.sang.common.domain.user.entity.User;
import io.ebean.DB;
import io.ebean.QueryIterator;
import io.ebean.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    void batchModifyTest() {

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

    @Test
    void batchSelectTest() {
        // ebean流式查询 结合数据库游标

        ArrayList<User> users = new ArrayList<>();
        User user = new User();
        users.add(user);

        // findEach 每次查询1000条
        DB.find(User.class).findEach(1000,list -> {
            users.addAll(list);
        });

        users.size();

        // iterate
        QueryIterator<User> iterate = DB.find(User.class).findIterate();
        while(iterate.hasNext()) {
            User next = iterate.next();
//            ...
        }

        // findStream
        List<User> collect = DB.find(User.class).findStream()
//                .filter(item -> {})
//                .map(item -> {})
                .collect(Collectors.toList());

        // findEachWhile
        DB.find(User.class).findEachWhile((User user1) -> {
//            ...
            return user1.getId() > 10000;
        });
    }
}
