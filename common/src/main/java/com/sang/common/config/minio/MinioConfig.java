package com.sang.common.config.minio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MinioConfig {

    private String url;
    private String accessKey;
    private String secretKey;
    private String api;
    private String path;

}
