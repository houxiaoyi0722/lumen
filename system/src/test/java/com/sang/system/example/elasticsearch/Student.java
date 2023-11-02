package com.sang.system.example.elasticsearch;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "log")
@Data
public class Student {
    @Id
    private String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String name;
    /**
     * 指定字段类型、分词器、查询分词器
     * index = false 表示不索引该字段，无法通过该字段查询数据
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart",index = false)
    private String price;
}
