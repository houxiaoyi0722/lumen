package com.sang.common.provider;

import cn.hutool.core.util.StrUtil;
import com.sang.common.config.auth.JwtAuthenticationToken;
import com.sang.common.domain.auth.authorization.token.dto.TokenDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static com.sang.common.constants.AuthConst.*;

/**
 * @author hxy
 * @date 2022/1/21 16:50
 **/
@Slf4j
@NoArgsConstructor
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {

    private JwtDecoder jwtDecoder;

    private JwtEncoder jwtEncoder;

    public JwtTokenAuthenticationProvider(JwtDecoder jwtDecoder, JwtEncoder jwtEncoder) {
        this.jwtDecoder = jwtDecoder;
        this.jwtEncoder = jwtEncoder;
    }

    @Value("${jwt.expire}")
    public long EXPIRY;

    @Value("${jwt.refresh-expiry}")
    public long REFRESH_EXPIRY;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = ((JwtAuthenticationToken)authentication).getToken();
        Jwt token = jwtDecoder.decode(principal);
        return getUsernamePasswordAuthenticationToken(principal, token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String jwt, Jwt token) {
        if (StrUtil.isNotEmpty(token.getSubject())) {
            // 从Token中解密获取用户角色
            String rolsStr = token.getClaim(ROLES).toString();
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (StrUtil.isNotBlank(rolsStr)) {
                String[] roles = rolsStr.split(ROLE_SPLIT);
                for (String s : roles) {
                    authorities.add(new SimpleGrantedAuthority(s));
                }
            }
            return new UsernamePasswordAuthenticationToken(token.getSubject(), jwt, authorities);
        }
        return null;
    }

}