package com.sang.system.domain;

import com.sang.domain.BaseModel;
import com.sang.system.domain.finder.DataDictionaryFinder;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * 数据字典 组
 */
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDictionary extends BaseModel {

    public static final DataDictionaryFinder finder = DataDictionaryFinder.builder().build();

//    @OneToMany
//    private DataDictionaryItem dataDictionaryItem;
    /**
     * 组id
     */
    @Column(length = 10,unique = true,nullable = false)
    private String groupValue;
    /**
     * 组名称
     */
    @Column(length = 50,nullable = false)
    private String groupKey;
    /**
     * 备注
     */
    @Column(length = 50)
    private String comment;

}
