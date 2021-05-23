package com.sang.system.domain;

import com.sang.domain.BaseModel;
import com.sang.system.domain.finder.DataDictionaryItemFinder;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDictionaryItem extends BaseModel {

    public static final DataDictionaryItemFinder finder = DataDictionaryItemFinder.builder().build();

//    @ManyToOne
//    private DataDictionary dataDictionary;

    @Column(length = 10,nullable = false)
    private String parentId;

    @Column(length = 100,nullable = false)
    private String itemValue;

    @Column(length = 100,nullable = false)
    private String itemKey;

    @Column(length = 10,nullable = false)
    private String language;

    @Column(length = 100)
    private String comment;

}
