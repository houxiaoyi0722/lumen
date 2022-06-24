package com.sang.system.controller.storage;

import com.sang.common.domain.storage.entity.Storage;
import com.sang.common.response.Result;
import com.sang.system.service.storage.StorageService;
import io.lettuce.core.dynamic.annotation.Param;
import io.minio.errors.*;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/lumen/storage")
@RestController
public class StorageController {

    @Resource
    private StorageService storageService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<Storage> upload(@Param("file") MultipartFile file,@Param("bucket") String bucket,@Param("businessCode") String businessCode, @Param("businessType") String businessType) throws InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, RegionConflictException {
        return Result.ok(storageService.upload(file,bucket,businessCode,businessType));
    }



}
