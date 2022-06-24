package com.sang.system.service.storage;

import com.sang.common.domain.storage.entity.Storage;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 对象存储管理
 * @author hxy
 * @date 2022/06/23
 */
public interface StorageService {

    /**
     * 文件上传
     * @param file
     * @return Storage存储对象包含url
     */
    Storage upload(MultipartFile file, String bucket, String businessCode, String businessType) throws NoSuchAlgorithmException, IOException, InvalidKeyException, RegionConflictException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, InvalidResponseException, XmlParserException, InternalException;
}
