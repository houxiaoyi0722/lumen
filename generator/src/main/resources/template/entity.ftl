package com.sang.common.domain.${domain}.entity;

import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.${domain}.entity.finder.${model}Finder;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * @author ${author}
 * @since ${createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@DbComment("${dbComment}")
public class ${model} extends BaseModel {

    public static final ${model}Finder finder = ${model}Finder.builder().build();

    private String test;
}
