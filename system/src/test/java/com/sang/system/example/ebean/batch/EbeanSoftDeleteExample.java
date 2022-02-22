package com.sang.system.example.ebean.batch;

import com.sang.common.domain.user.entity.User;
import io.ebean.DB;
import org.junit.jupiter.api.Test;

/**
 * @author hxy
 * @date 2022/2/22 10:22
 **/
public class EbeanSoftDeleteExample {


    @Test
    void ebeanSoftDeleteExample() {
        User user = new User();
        // update mybean set version=?, deleted=? where id=? and version=?; --bind(2,true,1,1,)
        boolean delete = DB.delete(user);

        // delete becomes an update if the bean has soft delete property
        DB.deletePermanent(user);

        // 逻辑删除后所有框架层面的正常查询都会忽略被逻辑删除的数据
        // 需要查询被逻辑删除的数据 则使用 includeSoftDeletes
        User one = DB.find(User.class).setIncludeSoftDeletes().setId(1).findOne();

    }
}
