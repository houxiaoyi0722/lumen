package com.sang.common.config;

/**
 * @author hxy
 * @date 2022/1/14 16:04
 **/
/*@Data
@EnableAutoConfiguration
@Configuration
@PropertySource("classpath:config.properties")
public class SysConfig {
    @Value("${rsaPublicKey}")
    private String rsaPublicKey;
    @Value("${rsaPrivateKey}")
    private String rsaPrivateKey;
    @Value("${encryptAESKey}")
    private String encryptAESKey;
    @Value("${encryptJWTKey}")
    private String encryptJWTKey;
    @Value("${accessTokenExpireTime}")
    private int accessTokenExpireTime;
    @Value("${refreshTokenExpireTime}")
    private int refreshTokenExpireTime;
    @Value("${shiroCacheExpireTime}")
    private int shiroCacheExpireTime;
    @Value("${fileCachePath}")
    private String fileCachePath;
    @Value("${loginUrl}")
    private String loginUrl;
    @Bean(value = "sysProperties",name = "sysProperties")
    public SysProperties init(){
        SysProperties sysProperties = new SysProperties();
        sysProperties.setRsaPublicKey(rsaPublicKey);
        sysProperties.setRsaPrivateKey(rsaPrivateKey);
        sysProperties.setEncryptAESKey(this.encryptAESKey);
        sysProperties.setEncryptJWTKey(this.encryptJWTKey);
        sysProperties.setAccessTokenExpireTime(this.accessTokenExpireTime);
        sysProperties.setRefreshTokenExpireTime(this.refreshTokenExpireTime);
        sysProperties.setShiroCacheExpireTime(this.shiroCacheExpireTime);
        sysProperties.setFileCachePath(fileCachePath);
        sysProperties.setLoginUrl(loginUrl);
        return sysProperties;
    }
}*/
