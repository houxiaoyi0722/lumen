package com.sang.common.domain.dict.entity;

import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.dict.entity.finder.DictionaryItemFinder;
import io.ebean.annotation.DbComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 数据字典明细
 */
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_dict_item")
@DbComment("数据字典明细")
public class DictionaryItem extends BaseModel {

    public static final DictionaryItemFinder finder = DictionaryItemFinder.builder().build();

    @NotNull
    @ManyToOne
    private Dictionary dictionary;

    /**
     * value
     */
    @DbComment("value")
    @Column(length = 100,nullable = false)
    private String itemValue;

    /**
     * key
     */
    @DbComment("key")
    @Column(length = 100,nullable = false)
    private String itemKey;

    /**
     * 备注
     */
    @DbComment("备注")
    @Column(length = 100)
    private String comment;

}
