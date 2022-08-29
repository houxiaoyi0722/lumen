package com.sang.common.domain.${domain?lower_case}.entity;

import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.${domain?lower_case}.entity.finder.${model}Finder;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * ${domainComment}
 * ${modelComment}
 * ${fileComment}
 * ${author} ${createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Entity
@Table
@DbComment("${dbComment}")
public class ${model} extends BaseModel {

    public static final ${model}Finder finder = ${model}Finder.builder().build();

}
