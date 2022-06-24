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
@Table(name = "STORAGE")
@DbComment("对象存储管理")
public class Storage extends BaseModel {

    /**
     * 原文件名
     */
    @Column(length = 100,nullable = false)
    @DbComment("原文件名")
    private String originalFileName;

    /**
     * 存储文件名（使用id代替）
     */
//    @Column(length = 100)
//    @DbComment("存储文件名")
//    private String newFileName;

    /**
     * 下载链接
     */
    @Column(length = 200,nullable = false)
    @DbComment("下载链接")
    private String url;

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
    private String fileType;

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
