package com.sang.system.domain.auth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hxy
 * @date 2022/1/17 10:56
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class TokenDto {
    /**
     * 访问令牌
     */
    private String token;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 访问令牌头前缀
     */
    private String tokenHead;
    /**
     * 有效时间（秒）
     */
    private Long expiresIn;
}
