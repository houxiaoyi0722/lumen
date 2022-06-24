package com.sang.system.service.storage.impl;

import cn.hutool.core.util.StrUtil;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.storage.entity.Storage;
import com.sang.common.snowId.SnowIdGenerator;
import com.sang.system.domain.storage.repo.StorageRepository;
import com.sang.system.service.storage.StorageService;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
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

    @Override
    public Storage upload(MultipartFile file, String bucket, String businessCode, String businessType) throws NoSuchAlgorithmException, IOException, InvalidKeyException, RegionConflictException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, InvalidResponseException, XmlParserException, InternalException {
        String originalFilename = file.getOriginalFilename() == null ? StringConst.EMPTY : file.getOriginalFilename();
        Storage storage = null;
        try {
            // 如果不为空，以传递的bucket存储文件，前提是bucket已经存在
            // 注意:！！！！！不能通过请求创建bucket！！！！！安全问题
            if (StrUtil.isNotBlank(bucket)) {
                if (!minioClient.bucketExists(bucket))
                    throw new InvalidBucketNameException(bucket,"bucket 不存在");
            } else {
                bucket = this.bucket;
                if (!minioClient.bucketExists(bucket))
                    minioClient.makeBucket(bucket);
            }
            final long size = file.getSize();
            final Long id = (Long)snowIdGenerator.nextValue();
            // 使用putObject上传一个文件到存储桶中。
            minioClient.putObject(bucket,id+"",file.getInputStream(), new PutObjectOptions(size,-1));

            final String objectUrl = minioClient.getObjectUrl(bucket, id + "");

            storage = Storage.builder()
                    .originalFileName(originalFilename)
                    .storageBucket(bucket)
                    .size(size)
                    .fileType(StrUtil.subAfter(originalFilename, StringConst.DOT,true))
                    .url(objectUrl)
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
}
