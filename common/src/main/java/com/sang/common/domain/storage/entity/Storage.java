package com.sang.common.domain.storage.entity;


import com.sang.common.domain.base.entity.BaseModel;
import io.ebean.annotation.DbComment;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Builder
@Table(name = "storage")
@DbComment("对象存储管理")
@NoArgsConstructor
@AllArgsConstructor
public class Storage extends BaseModel {

    /**
     * 原文件名
     */
    @Column(length = 100,nullable = false)
    @DbComment("原文件名")
    private String originalFileName;

    /**
     * 原文件名
     */
    @Column(length = 100,nullable = false)
    @DbComment("原文件名")
    private String etag;

    /**
     * 存储文件名（使用id代替）
     */
//    @Column(length = 100)
//    @DbComment("存储文件名")
//    private String newFileName;

    /**
     * 对象存储地址
     */
    @Column(length = 200,nullable = false)
    @DbComment("对象存储地址")
    private String object;

    /**
     * 存储桶
     */
    @Column(length = 200,nullable = false)
    @DbComment("存储桶")
    private String storageBucket;

    /**
     * 文件类型/后缀名
     */
    @Column(length = 50)
    @DbComment("文件类型/后缀名")
    private String suffix;
    /**
     * 版本id
     */
    @Column(length = 50)
    @DbComment("版本id")
    private String versionId;

    /**
     * 文件大小/b
     */
    @Column(length = 200)
    @DbComment("文件大小/b")
    private Long size;

    /**
     * 业务代码
     */
    @Column(length = 50)
    @DbComment("业务代码")
    private String businessCode;

    /**
     * 业务类型
     */
    @Column(length = 50)
    @DbComment("业务类型")
    private String businessType;

}
