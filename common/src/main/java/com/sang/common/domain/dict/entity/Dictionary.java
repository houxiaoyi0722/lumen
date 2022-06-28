package com.sang.common.domain.dict.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.dict.entity.finder.DictionaryFinder;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

/**
 * 数据字典 组
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Entity
@Table(name = "data_dictionary")
@DbComment("数据字典")
public class Dictionary extends BaseModel {

    public static final DictionaryFinder finder = DictionaryFinder.builder().build();

    @JsonIgnore
    @OneToMany(mappedBy = "dictionary", cascade = PERSIST)
    private List<DictionaryItem> dictionaryItems;

    /**
     * 组id
     */
    @DbComment("组id")
    @Column(length = 10,nullable = false,unique = true)
    private String groupId;

    /**
     * 组名称
     */
    @DbComment("组名称")
    @Column(length = 10,nullable = false,unique = true)
    private String groupName;

    /**
     * 备注
     */
    @DbComment("备注")
    @Column(length = 50)
    private String comment;

}
