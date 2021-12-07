package com.sang.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hxy
 * @date 2021/12/7 18:13
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "datasource.db")
public class EbeanDataSourceConfig {
    private String username;
    private String password;
    private String url;
}
