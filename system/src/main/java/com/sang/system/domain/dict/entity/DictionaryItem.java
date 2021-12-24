package com.sang.system.domain.dict.entity;

import com.sang.domain.entity.BaseModel;
import com.sang.system.domain.dict.entity.finder.DictionaryItemFinder;
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
public class DictionaryItem extends BaseModel {

    public static final DictionaryItemFinder finder = DictionaryItemFinder.builder().build();

    @NotNull
    @ManyToOne(optional = false)
    private Dictionary dictionary;

    @Column(length = 100,nullable = false)
    private String itemValue;

    @Column(length = 100,nullable = false)
    private String itemKey;

    @Column(length = 100)
    private String comment;

}
