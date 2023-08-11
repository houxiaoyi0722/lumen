package com.sang.common.handle;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sang.common.constants.AuthConst;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.auth.authentication.token.dto.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.sang.common.constants.AuthConst.TOKEN_HEADER;

/**
 * @author hxy
 * @date 2022/1/20 18:01
 **/
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${jwt.expire}")
    public long EXPIRY;

    @Value("${jwt.refresh-expiry}")
    public long REFRESH_EXPIRY;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private JwtEncoder encoder;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Instant now = Instant.now();
        // @formatter:off
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(AuthConst.ROLE_SPLIT));

        String id = UUID.randomUUID().toString().replaceAll(StringConst.MINUS, StringConst.EMPTY);

        response.setHeader(StringConst.CONTENT_TYPE, StringConst.APPLICATION_JSON);
        TokenDto token = getToken(authentication, now, scope, id);

        redisTemplate.boundValueOps(AuthConst.TOKEN_JWT + authentication.getName()).set(token.getToken(), EXPIRY, TimeUnit.SECONDS);
        redisTemplate.boundValueOps(AuthConst.REFRESH_TOKEN_JWT + authentication.getName()).set(token.getRefreshToken(), REFRESH_EXPIRY, TimeUnit.SECONDS);
        response.getWriter().write(objectMapper.writeValueAsString(token));
    }

    private TokenDto getToken(Authentication authentication, Instant now, String scope, String id) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(AuthConst.SELF)
                .id(id)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRY))
                .subject(authentication.getName())
                .claim(AuthConst.ROLES, scope)
                .build();

        JwtClaimsSet refreshClaims = JwtClaimsSet.builder()
                .issuer(AuthConst.SELF)
                .id(id)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(REFRESH_EXPIRY))
                .subject(authentication.getName())
                .claim(AuthConst.ROLES, scope)
                .build();

        Jwt encode = this.encoder.encode(JwtEncoderParameters.from(claims));
        String tokenValue = encode.getTokenValue();
        String refreshTokenValue = this.encoder.encode(JwtEncoderParameters.from(refreshClaims)).getTokenValue();

        // @formatter:on
        return TokenDto.builder()
                .token(tokenValue)
                .refreshToken(refreshTokenValue)
                .tokenHead(TOKEN_HEADER)
                .expiresIn(DateUtil.between(Date.from(Objects.requireNonNull(encode.getExpiresAt())), DateUtil.date(), DateUnit.SECOND))
                .build();
    }
}
