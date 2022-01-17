package com.sang.common.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sang.common.domain.auth.dto.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static com.sang.common.constants.AuthConst.*;

/**
 * @author hxy
 * @date 2022/1/17 11:48
 **/
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.expire}")
    public long EXPIRY;

    @Value("${jwt.refresh-expiry}")
    public long REFRESH_EXPIRY;

    @Resource
    private JwtDecoder jwtDecoder;

    @Resource
    private JwtEncoder jwtEncoder;

    /**
     * 验证jwt是否有效,
     * 如果jwt非法,报错
     * jwt过期,查看refreshToken是否过期,过期要求重新登陆
     * 如果refreshToken未过期,签发新的令牌
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,HttpServletResponse response) {
        Instant now = Instant.now();

        // 获取jwt
        String jwtStr = request.getHeader(AUTHENTICATION);
        if (StrUtil.isNotEmpty(jwtStr) && jwtStr.startsWith(TOKEN_HEADER)) {
            // 去除 tokenHeader
            jwtStr = jwtStr.replace(jwtStr, TOKEN_HEADER);
            if (StrUtil.isNotEmpty(jwtStr)) {
                TokenDto tokenDto = JSONUtil.toBean(jwtStr, TokenDto.class);

                Jwt token = null;
                Jwt freshToken = null;
                try {
                    token = jwtDecoder.decode(tokenDto.getToken());
                    return getUsernamePasswordAuthenticationToken(tokenDto, token);
                } catch (JwtValidationException e) {
                    // jwt 签名验证通过后,如果过期(默认只有过期校验)或者其他类型验证错误会抛出该异常
                    // 过期后验证refreshToken是否过期,未过期重新签发签名给客户端
                    freshToken = getFreshToken(now, tokenDto);
                    response.setHeader(AUTHENTICATION,freshToken.getTokenValue());
                }
                return getUsernamePasswordAuthenticationToken(tokenDto, freshToken);
            }
        }
        return null;
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

}
