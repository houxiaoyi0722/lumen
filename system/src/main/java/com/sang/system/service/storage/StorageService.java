package com.sang.system.service.storage;

import com.sang.common.domain.storage.entity.Storage;
import com.sang.common.exception.BusinessException;
import io.minio.errors.MinioException;
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

    /**
     * 通过业务代码获取下载链接
     * @param businessCode 业务代码
     * @param businessType 业务类型
     * @param expiry 超时时间（秒）\默认24小时
     * @return 下载链接
     * @throws MinioException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    String getPresignedObjectUrlByBusiness(String businessCode, String businessType, int expiry) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException, BusinessException;

    /**
     * 通过id获取下载链接
     * @param id id
     * @param expiry 超时时间（秒）\默认24小时
     * @return 下载链接
     * @throws MinioException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    String getPresignedObjectUrlById(Long id, int expiry) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException, BusinessException;

    /**
     * 下载文件获取输入流
     * @param businessCode
     * @param businessType
     * @return
     */
    void getObjectByBusiness(String businessCode, String businessType) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException, BusinessException;

    /**
     * 下载文件获取输入流
     * @param id
     */
    void getObjectById(Long id) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException, BusinessException;
}
