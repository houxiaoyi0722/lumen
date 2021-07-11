package com.sang.system.domain.entity;

import com.sang.domain.entity.BaseModel;
import com.sang.system.domain.entity.finder.DataDictionaryFinder;
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
@Table(name = "DATA_DICTIONARY")
public class DataDictionary extends BaseModel {

    public static final DataDictionaryFinder finder = DataDictionaryFinder.builder().build();

    @OneToMany(mappedBy = "dataDictionary", cascade = PERSIST)
    private List<DataDictionaryItem> dataDictionaryItems;
    /**
     * 组id
     */
    @Column(length = 10,nullable = false)
    private String groupId;
    /**
     * 组名称
     */
    @Column(length = 50,nullable = false)
    private String groupName;
    /**
     * 备注
     */
    @Column(length = 50)
    private String comment;

}
