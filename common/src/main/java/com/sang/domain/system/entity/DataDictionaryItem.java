package com.sang.domain.entity;

import com.sang.domain.entity.finder.DataDictionaryItemFinder;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
