package com.sang.common.domain.storage.entity;


import cn.hutool.extra.spring.SpringUtil;
import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.storage.entity.finder.StorageFinder;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Index;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Slf4j
@Setter
@Getter
@Entity
@SuperBuilder
@Index(name = "storage_bucket",columnNames = "storage_bucket")
@Index(name = "suffix",columnNames = "suffix")
@Index(name = "business_type",columnNames = "business_type")
@Index(name = "business_code",columnNames = "business_code")
@Table(name = "sys_storage")
@DbComment("对象存储管理")
@NoArgsConstructor
@AllArgsConstructor
public class Storage extends BaseModel {

    public static final StorageFinder finder = StorageFinder.builder().build();

    /**
     * 原文件名
     */
    @Column(length = 100,nullable = false)
    @DbComment("原文件名")
    private String originalFileName;

    /**
     * etag
     */
    @Column(length = 100,nullable = false)
    @DbComment("etag")
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
     * ContentType文件类型/请求头
     */
    @Column(length = 50)
    @DbComment("文件类型/请求头")
    private String contentType;
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

    public String getDownLoadUrl() {

        MinioClient minioClient = (MinioClient) SpringUtil.getBean(MinioClient.class);

        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(this.storageBucket)
                    .object(this.object)
                    .expiry(60 * 60 * 24) // 默认超时时间一天
                    .build());
        } catch (Exception e) {
            log.error("",e);
        }
        return "";
    }
}
