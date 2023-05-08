package com.sang.system.controller.storage;

import com.sang.common.domain.storage.dto.StorageDto;
import com.sang.common.domain.storage.mapper.StorageMapper;
import com.sang.common.exception.BusinessException;
import com.sang.common.response.Result;
import com.sang.system.service.storage.StorageService;
import io.lettuce.core.dynamic.annotation.Param;
import io.minio.errors.MinioException;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/oss")
@RestController
public class StorageController {

    @Resource
    private StorageService storageService;

    private final StorageMapper storageMapper = StorageMapper.mapper;


    /**
     * 自定义文件上传
     * @param file 文件
     * @param bucket 桶 为空为默认bucket，指定bucket需要先创建才能上传成功
     * @param businessCode 业务代码
     * @param businessType 业务类型
     * @return StorageDto
     * @throws MinioException minio异常
     * @throws IOException io异常
     * @throws NoSuchAlgorithmException 算法异常
     * @throws InvalidKeyException key异常
     */
    @PutMapping("/upload")
    public Result<StorageDto> upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "bucket",required = false) String bucket, @RequestParam("businessCode") String businessCode, @Param("businessType") String businessType) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        return Result.ok(storageMapper.storageToDto(storageService.upload(file,bucket,businessCode,businessType)));
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
    public Result<StorageDto> uploadWithOutBusiness(@RequestParam("file") MultipartFile file,@RequestParam(value = "bucket",required = false) String bucket) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        return Result.ok(storageMapper.storageToDto(storageService.uploadWithOutBusiness(file,bucket)));
    }

    /**
     * 通过业务代码获取下载链接
     * @param businessCode 业务代码
     * @param businessType 业务类型
     * @param expiry 超时时间（秒）\默认24小时
     * @return 下载链接
     * @throws BusinessException
     * @throws MinioException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    @GetMapping("/objectUrl")
    public Result<String> getPresignedObjectUrlByBusiness(@RequestParam("businessCode")  String businessCode, @RequestParam("businessType")  String businessType, @RequestParam(value = "expiry",required = false,defaultValue = "-1")  int expiry) throws BusinessException, MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        return Result.ok(storageService.getPresignedObjectUrlByBusiness(businessCode, businessType, expiry));
    }

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
    @GetMapping(value = "/objectUrlById")
    public Result<String> getPresignedObjectUrlById(@RequestParam("id") Long id, @RequestParam(value = "expiry",required = false,defaultValue = "-1") int expiry) throws BusinessException, MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        return Result.ok(storageService.getPresignedObjectUrlById(id, expiry));
    }

    /**
     * 下载文件
     * @param businessCode
     * @param businessType
     * @throws MinioException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BusinessException
     */
    @GetMapping("/object")
    public void getObjectByBusiness(@RequestParam("businessCode")  String businessCode, @RequestParam("businessType")  String businessType) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException, BusinessException {
        storageService.getObjectByBusiness(businessCode, businessType);
    }

    /**
     * 下载文件
     * @param id
     * @throws MinioException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BusinessException
     */
    @GetMapping("/objectById")
    public void getObjectById(@RequestParam("id") Long id) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException, BusinessException {
        storageService.getObjectById(id);
    }





}
