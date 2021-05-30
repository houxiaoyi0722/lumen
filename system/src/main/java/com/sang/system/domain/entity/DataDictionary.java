package com.sang.system.domain.entity;

import com.sang.entity.BaseModel;
import com.sang.system.domain.entity.finder.DataDictionaryFinder;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

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

    @OneToMany(mappedBy = "dataDictionary", cascade = PERSIST)
    private List<DataDictionaryItem> dataDictionaryItems;
    /**
     * 组id
     */
    @Column(length = 10,nullable = false)
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
