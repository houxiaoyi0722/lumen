package com.sang.system;

import com.sang.entity.Product;
import com.sang.entity.query.QProduct;
import io.ebean.DB;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SystemApplicationTests {

    @Test
    void contextLoads() {
//        Product build = Product.builder().name("111").id(1L).sku("411").build();
//        DB.save(build);
//        Product one = QProduct.alias().id.equalTo(1L).findOne();
    }

}
