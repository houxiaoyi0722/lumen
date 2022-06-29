package com.sang.system.service.storage.impl;

import cn.hutool.core.util.StrUtil;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.storage.entity.Storage;
import com.sang.common.snowId.SnowIdGenerator;
import com.sang.system.domain.storage.repo.StorageRepository;
import com.sang.system.service.storage.StorageService;
import io.ebean.annotation.Transactional;
import io.minio.*;
import io.minio.errors.MinioException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@CommonsLog
@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageRepository storageRepository;

    @Resource
    private MinioClient minioClient;

    @Resource
    private SnowIdGenerator snowIdGenerator;

    @Value("${minio.bucket.master}")
    private String bucket;

    @Value("${minio.bucket.master}")
    private String path;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Storage upload(MultipartFile file, String bucket, String businessCode, String businessType) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        String originalFilename = file.getOriginalFilename() == null ? StringConst.EMPTY : file.getOriginalFilename();
        Storage storage;
        try {
            // 如果不为空，以传递的bucket存储文件，前提是bucket已经存在
            // 注意:！！！！！不能通过请求创建bucket！！！！！安全问题
            if (StrUtil.isNotBlank(bucket)) {
                if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build()))
                    throw new IllegalArgumentException("bucket 不存在: " + bucket);
            } else {
                bucket = this.bucket;
                if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build()))
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
            long size = file.getSize();
            Long id = (Long)snowIdGenerator.nextValue();
            String fileType = StrUtil.subAfter(originalFilename, StringConst.DOT, true);

            // 使用putObject上传一个文件到存储桶中。
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object("/lumen/" + id)
                            .stream(file.getInputStream(),size,-1)
                            .contentType(file.getContentType())
                            .build()
            );

            storage = Storage.builder()
                    .originalFileName(originalFilename)
                    .storageBucket(bucket)
                    .size(size)
                    .fileType(fileType)
                    .url("")
                    .businessCode(businessCode)
                    .businessType(businessType)
                    .build();

            storage.setId(id);
            storageRepository.save(storage);
        } catch (MinioException e) {
            log.error(e);
            throw e;
        }
        return storage;
    }

    @Override
    public Storage uploadWithOutBusiness(MultipartFile file, String bucket) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        return this.upload(file,bucket,null,null);
    }
}
