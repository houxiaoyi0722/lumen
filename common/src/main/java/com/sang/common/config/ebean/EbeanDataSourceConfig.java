package com.sang.common.config.ebean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author hxy
 * @date 2021/12/7 18:13
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "datasource.db")
public class EbeanDataSourceConfig {
    private String username;
    private String password;
    private String url;
    private String jdbcUrl;
    private int minIdle = 5;
    private long idleTimeout = 600000L;
    private long maxLifetime = 1800000L;
    private long connectionTimeout = 30000L;
    private int maxPoolSize = 10;
    private String driverClassName;
}
