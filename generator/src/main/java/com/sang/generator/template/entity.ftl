package com.sang.common.domain.${domain}.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.${domain}.entity.finder.${model}Finder;
import io.ebean.annotation.DbComment;

import java.util.List;

/**
* ${comment}
* ${auther}
* ${createDate?string("yyyy-MM-dd HH:mm:ss")}
*/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Entity
@Table
@DbComment("${comment}")
public class ${model} extends BaseModel {

    public static final ${model}Finder finder = ${model}Finder.builder().build();

}
