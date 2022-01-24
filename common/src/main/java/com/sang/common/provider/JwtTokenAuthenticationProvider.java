package com.sang.common.provider;

import cn.hutool.core.util.StrUtil;
import com.sang.common.config.auth.JwtAuthenticationToken;
import com.sang.common.domain.auth.dto.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.*;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static com.sang.common.constants.AuthConst.*;

/**
 * @author hxy
 * @date 2022/1/21 16:50
 **/
@Slf4j
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private JwtDecoder jwtDecoder;
    @Resource
    private JwtEncoder jwtEncoder;

    @Value("${jwt.expire}")
    public long EXPIRY;

    @Value("${jwt.refresh-expiry}")
    public long REFRESH_EXPIRY;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        TokenDto principal = ((JwtAuthenticationToken)authentication).getToken();
        Jwt token = null;
        Jwt freshToken = null;
        try {
            token = jwtDecoder.decode(principal.getToken());
            return getUsernamePasswordAuthenticationToken(principal, token);
        } catch (JwtValidationException e) {
            // jwt 签名验证通过后,如果过期(默认只有过期校验)或者其他类型验证错误会抛出该异常
            // 过期后验证refreshToken是否过期,未过期重新签发签名给客户端
            Collection<OAuth2Error> errors = e.getErrors();
            errors.forEach(oAuth2Error -> log.error(oAuth2Error.getErrorCode()));
            freshToken = getFreshToken(Instant.now(), principal);
            // todo jwt过期刷新
//            response.setHeader(AUTHORIZATION,freshToken.getTokenValue());
        }
        return getUsernamePasswordAuthenticationToken(principal, freshToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }

    private Jwt getFreshToken(Instant now, TokenDto tokenDto) {
        Jwt refreshToken = jwtDecoder.decode(tokenDto.getRefreshToken());

        JwtClaimsSet freshClaims = JwtClaimsSet.builder()
                .issuer(refreshToken.getIssuer().toString())
                .id(refreshToken.getId())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRY))
                .subject(refreshToken.getSubject())
                .claim(ROLES, refreshToken.getClaim(ROLES))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(freshClaims));
    }


    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(TokenDto tokenDto, Jwt token) {
        if (StrUtil.isNotEmpty(token.getSubject())) {
            // 从Token中解密获取用户角色
            String[] roles = token.getClaim(ROLES).toString().split(ROLE_SPLIT);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (String s : roles) {
                authorities.add(new SimpleGrantedAuthority(s));
            }
            return new UsernamePasswordAuthenticationToken(token.getSubject(), tokenDto.getToken(), authorities);
        }
        return null;
    }

}
