package com.sang.system;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.common.domain.dict.entity.DictionaryItem;
import com.sang.common.domain.auth.authorization.user.entity.User;
import io.ebean.DB;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootTest
class SystemApplicationTests {

//    @Test
    void contextLoads() {
        Dictionary build = Dictionary.builder().groupId("22").build();
        DictionaryItem build1 = DictionaryItem.builder().itemKey("11").itemValue("132").build();
        ArrayList<DictionaryItem> objects = new ArrayList<>();
        objects.add(build1);
        build.setDictionaryItems(objects);

        DB.save(build);
    }

//    @Test
    void addUser() {

        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User build = User.builder().userName("hxy").name("hxy").password(bCryptPasswordEncoder.encode("123456"))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .build();
        DB.save(build);
    }

}
