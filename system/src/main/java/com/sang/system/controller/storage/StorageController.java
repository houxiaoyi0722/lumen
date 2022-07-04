package com.sang.system.controller.storage;

import com.sang.common.domain.storage.entity.Storage;
import com.sang.common.exception.BusinessException;
import com.sang.common.response.Result;
import com.sang.system.service.storage.StorageService;
import io.lettuce.core.dynamic.annotation.Param;
import io.minio.errors.MinioException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 对象存储管理
 * @author hxy
 * @date 2022/1/25 16:01
 **/
@RequestMapping("/lumen/oss")
@RestController
public class StorageController {

    @Resource
    private StorageService storageService;

    /**
     * 自定义文件上传
     * @param file 文件
     * @param bucket 桶 为空为默认bucket，指定bucket需要先创建才能上传成功
     * @param businessCode 业务代码
     * @param businessType 业务类型
     * @return
     * @throws MinioException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    @PutMapping("/upload")
    public Result<Storage> upload(@Param("file") MultipartFile file,@Param("bucket") String bucket,@Param("businessCode") String businessCode, @Param("businessType") String businessType) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        return Result.ok(storageService.upload(file,bucket,businessCode,businessType));
    }

    /**
     * 不带业务标识的上传
     * @param file 文件
     * @param bucket 桶 为空为默认bucket，指定bucket需要先创建才能上传成功
     * @return
     * @throws MinioException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    @PutMapping("/uploadWithOutBusiness")
    public Result<Storage> uploadWithOutBusiness(@Param("file") MultipartFile file,@Param("bucket") String bucket) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        return Result.ok(storageService.uploadWithOutBusiness(file,bucket));
    }

    @GetMapping("/objectUrl")
    public Result<String> getPresignedObjectUrlByBusiness(@Param("businessCode")  String businessCode, @Param("businessType")  String businessType, @Param("expiry")  int expiry) throws BusinessException, MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        return Result.ok(storageService.getPresignedObjectUrlByBusiness(businessCode, businessType, expiry));
    }

    @GetMapping(value = "/objectUrl")
    public Result<String> getPresignedObjectUrlById(@Param("id") Long id, @Param("expiry") int expiry) throws BusinessException, MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        return Result.ok(storageService.getPresignedObjectUrlById(id, expiry));
    }





}
