package com.sang.common.config.minio;

import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.MinioClient;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.InputStream;

@CommonsLog
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Configuration
public class MinioConfiguration {

    @Value("${minio.credentials}")
    private String credentials;

    @Resource
    private ObjectMapper objectMapper;


    @SneakyThrows
    @Bean
    public MinioClient minioClient() {
//         使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        ClassPathResource classPathResource = new ClassPathResource(credentials);
        InputStream fileInputStream = classPathResource.getInputStream();

        FastByteArrayOutputStream read = IoUtil.read(fileInputStream);
        MinioConfig minioConfig = objectMapper.readValue(read.toString(), MinioConfig.class);

        return MinioClient.builder()
                .endpoint(minioConfig.getUrl())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }


}
