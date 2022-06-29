package com.sang.common.config;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@CommonsLog
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Configuration
public class MinioConfiguration {

    @Value("${minio.credentials}")
    private String credentials;

    private String url;
    private String accessKey;
    private String secretKey;
    private String api;
    private String path;

    @Bean
    public MinioClient minioClient() throws FileNotFoundException {
//         使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        FastByteArrayOutputStream read = null;
        FileInputStream fileInputStream = null;
        MinioConfiguration minioConfig;
        try {
            fileInputStream = new FileInputStream(ResourceUtils.getFile(credentials));
            read = IoUtil.read(fileInputStream);
            minioConfig = JSONUtil.toBean(read.toString(), MinioConfiguration.class);
        } finally {
            IoUtil.close(fileInputStream);
            IoUtil.close(read);
        }

        return MinioClient.builder()
                .endpoint(minioConfig.getUrl())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }


}
