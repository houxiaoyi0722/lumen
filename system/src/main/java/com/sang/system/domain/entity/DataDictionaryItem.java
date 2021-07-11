package com.sang.system.domain.entity;

import com.sang.domain.entity.BaseModel;
import com.sang.system.domain.entity.finder.DataDictionaryItemFinder;
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
public class DataDictionaryItem extends BaseModel {

    public static final DataDictionaryItemFinder finder = DataDictionaryItemFinder.builder().build();

    @NotNull
    @ManyToOne(optional = false)
    private DataDictionary dataDictionary;

    @Column(length = 100,nullable = false)
    private String itemValue;

    @Column(length = 100,nullable = false)
    private String itemKey;

    @Column(length = 100)
    private String comment;

}
