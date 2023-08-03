package com.sang.system.example.elasticsearch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Optional;

@SpringBootTest
public class ElasticsearchExample {

    @Resource
    private StudentRepository studentRepository;


    @Test
    public void test1() {
        Student student = new Student();
        student.setId("111");
        student.setName("2222");
        student.setPrice("33333");
        Student save = studentRepository.save(student);
        Optional<Student> byId = studentRepository.findById("111");
        Student student1 = byId.get();

    }

}
