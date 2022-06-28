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
     * @param bucket
     * @param businessCode
     * @param businessType
     * @return Storage存储对象包含url
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws MinioException
     */
    Storage upload(MultipartFile file, String bucket, String businessCode, String businessType) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException;

    /**
     * 不带业务标识的上传
     * @param file
     * @param bucket
     * @return
     */
    Storage uploadWithOutBusiness(MultipartFile file, String bucket) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException;
}
