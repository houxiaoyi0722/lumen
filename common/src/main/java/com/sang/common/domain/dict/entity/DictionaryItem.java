package com.sang.common.domain.dict.entity;

import com.sang.common.domain.dict.entity.finder.DictionaryItemFinder;
import com.sang.common.domain.base.entity.BaseModel;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DATA_DICTIONARY_ITEM")
@DbComment("数据字典明细")
public class DictionaryItem extends BaseModel {

    public static final DictionaryItemFinder finder = DictionaryItemFinder.builder().build();

    @NotNull
    @ManyToOne
    private Dictionary dictionary;

    @DbComment("value")
    @Column(length = 100,nullable = false)
    private String itemValue;

    @DbComment("key")
    @Column(length = 100,nullable = false)
    private String itemKey;

    @DbComment("备注")
    @Column(length = 100)
    private String comment;

}
