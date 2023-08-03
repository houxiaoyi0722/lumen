package com.sang.system.example.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StudentRepository extends ElasticsearchRepository<Student, String> {
}
