package com.sang.common.config;

import cn.hutool.json.JSONUtil;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
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
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        MinioConfig minioConfig = JSONUtil.toBean(credentials, MinioConfig.class);
        return new MinioClient(minioConfig.getUrl(), minioConfig.getAccessKey(), minioConfig.getAccessKey());
    }


}
