package com.sang.system.service.storage.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.sang.common.constants.StorageEnum;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.storage.entity.Storage;
import com.sang.common.exception.BusinessException;
import com.sang.common.snowId.SnowIdGenerator;
import com.sang.system.domain.storage.repo.StorageRepository;
import com.sang.system.service.storage.StorageService;
import io.ebean.annotation.Transactional;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    public static final String CONTENT_TYPE = "Content-type";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String ATTACHMENT_FILENAME = "attachment;filename={}";
    @Resource
    private StorageRepository storageRepository;

    @Resource
    private MinioClient minioClient;

    @Resource
    private SnowIdGenerator snowIdGenerator;

    @Value("${minio.bucket.master}")
    private String bucket;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Storage upload(MultipartFile file, String bucket, String businessCode, String businessType) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        String originalFilename = file.getOriginalFilename() == null ? StringConst.EMPTY : file.getOriginalFilename();
        // 如果不为空，以传递的bucket存储文件，前提是bucket已经存在
        // 注意:！！！！！不能通过请求创建bucket！！！！！安全问题
        bucket = checkBucket(bucket);
        long size = file.getSize();
        Long id = (Long)snowIdGenerator.nextValue();

        String suffix = StrUtil.subAfter(originalFilename, StringConst.DOT, true);

        // 使用putObject上传一个文件到存储桶中。
        String path = StrUtil.isNotBlank(businessType)? businessType + StringConst.FORWARD_SLASH : StringConst.EMPTY;
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(path + id + (StrUtil.isNotBlank(suffix) ? StringConst.DOT + suffix : StringConst.EMPTY))
                        .stream(file.getInputStream(),size,-1)
                        .contentType(file.getContentType())
                        .build()
        );

        Storage storage = Storage.builder()
                .originalFileName(originalFilename)
                .etag(objectWriteResponse.etag())
                .storageBucket(objectWriteResponse.bucket())
                .size(size)
                .suffix(suffix)
                .contentType(file.getContentType())
                .object(objectWriteResponse.object())
                .versionId(objectWriteResponse.versionId())
                .businessCode(businessCode)
                .businessType(businessType)
                .build();

        storage.setId(id);
        storageRepository.save(storage);
        return storage;
    }

    @Override
    public Storage uploadWithOutBusiness(MultipartFile file, String bucket) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        return this.upload(file,bucket,null,null);
    }


    @Override
    public String getPresignedObjectUrlByBusiness(String businessCode, String businessType, int expiry) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException, BusinessException {

        Storage storage = storageRepository.findByBusiness(businessCode, businessType);

        return getPresignedObjectUrl(expiry, storage);
    }

    @Override
    public String getPresignedObjectUrlById(Long id, int expiry) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException, BusinessException {

        Storage storage = storageRepository.findById(id);

        return getPresignedObjectUrl(expiry, storage);
    }

    @Override
    public void getObjectByBusiness(String businessCode, String businessType) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException, BusinessException {

        Storage storage = storageRepository.findByBusiness(businessCode, businessType);

        downloadFile(storage);
    }

    @Override
    public void getObjectById(Long id) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException, BusinessException {
        Storage storage = storageRepository.findById(id);

        downloadFile(storage);
    }

    private void downloadFile(Storage storage) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException, BusinessException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = requestAttributes.getResponse();

        if (storage == null)
            throw new BusinessException(StorageEnum.FILE_NOT_EXIST.getCode(),StorageEnum.FILE_NOT_EXIST.getMessage());

        GetObjectResponse objectResponse = minioClient.getObject(GetObjectArgs.builder()
                .bucket(storage.getStorageBucket())
                .object(storage.getObject())
                .build());

        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        try {
            // 设置信息给客户端不解析
            // 设置CONTENT_TYPE，即告诉客户端所发送的数据属于什么类型
            httpServletResponse.setHeader(CONTENT_TYPE, storage.getContentType());

            // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
            httpServletResponse.setHeader(CONTENT_DISPOSITION, StrUtil.format(ATTACHMENT_FILENAME, storage.getOriginalFileName()));

            IoUtil.copy(objectResponse, outputStream);
        } finally {
            if (objectResponse != null) {
                IoUtil.close(objectResponse);
            }
            if (outputStream != null) {
                IoUtil.close(outputStream);
            }
        }
    }


    private String getPresignedObjectUrl(int expiry, Storage storage) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, XmlParserException, ServerException, BusinessException {
        if (storage == null)
            throw new BusinessException(StorageEnum.FILE_NOT_EXIST.getCode(), StorageEnum.FILE_NOT_EXIST.getMessage());;

        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(storage.getStorageBucket())
                .object(storage.getObject())
                .expiry(expiry == -1 || expiry == 0 ? 60 * 60 * 24 : expiry) // 默认超时时间一天
                .build());
    }

    private String checkBucket(String bucket) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        if (StrUtil.isNotBlank(bucket)) {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build()))
                throw new IllegalArgumentException("bucket 不存在: " + bucket);
        } else {
            bucket = this.bucket;
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build()))
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
        return bucket;
    }
}
