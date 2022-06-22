package com.sang.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MinioConfig {
    private String url;
    private String accessKey;
    private String secretKey;
    private String api;
    private String path;
}
