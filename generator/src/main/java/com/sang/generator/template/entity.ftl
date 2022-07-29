package com.sang.common.domain.${domain}.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.${domain}.entity.finder.${domain}Finder;
import io.ebean.annotation.DbComment;

import java.util.List;

/**
* ${comment}
* ${auther}
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
public class ${domain} extends BaseModel {

    public static final ${domain}Finder finder = ${domain}Finder.builder().build();

}
