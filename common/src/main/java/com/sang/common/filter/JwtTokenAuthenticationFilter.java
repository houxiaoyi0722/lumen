package com.sang.common.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sang.common.config.auth.JwtAuthenticationToken;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.auth.authorization.token.dto.TokenDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sang.common.constants.AuthConst.AUTHORIZATION;
import static com.sang.common.constants.AuthConst.TOKEN_HEADER;

/**
 * @author hxy
 * @date 2022/1/17 11:48
 **/
@Getter
@Setter
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {


    @Resource
    private ObjectMapper objectMapper;

    private RequestMatcher requiresAuthenticationRequestMatcher;
    private List<RequestMatcher> permissiveRequestMatchers;
    private AuthenticationManager authenticationManager;

    // 去除默认成功后处理
    private AuthenticationSuccessHandler successHandler = null;
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();


    public JwtTokenAuthenticationFilter() {
        this.requiresAuthenticationRequestMatcher = new RequestHeaderRequestMatcher(AUTHORIZATION);
    }

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

        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = null;
        AuthenticationException failed = null;
        try {
            JwtAuthenticationToken jwtAuthenticationToken = getAuthentication(request, response);
            authentication = this.getAuthenticationManager().authenticate(jwtAuthenticationToken);
        } catch (BadJwtException e) {
            logger.error(e.getMessage(), e);
            failed = new InsufficientAuthenticationException(e.getMessage(), e);
        } catch (InternalAuthenticationServiceException e) {
            logger.error("An internal error occurred while trying to authenticate the user.", e);
            failed = e;
        } catch (AuthenticationException e) {
            // Authentication failed
            failed = e;
        }

        if(authentication != null) {
            successfulAuthentication(request, response, chain, authentication);
        } else if(!permissiveRequest(request)){
            unsuccessfulAuthentication(request, response, failed);
            return;
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private JwtAuthenticationToken getAuthentication(HttpServletRequest request,HttpServletResponse response) throws JsonProcessingException {

        // 获取jwt
        String jwtStr = request.getHeader(AUTHORIZATION);
        if (StrUtil.isNotEmpty(jwtStr)) {
            if (jwtStr.startsWith(TOKEN_HEADER)) {
                // 去除 tokenHeader
                jwtStr = jwtStr.replace(TOKEN_HEADER, StringConst.EMPTY);
                if (StrUtil.isNotEmpty(jwtStr)) {
                    TokenDto tokenDto = objectMapper.readValue(jwtStr, TokenDto.class);
                    return new JwtAuthenticationToken(tokenDto);
                } else {
                    throw new InsufficientAuthenticationException("JWT is Empty");
                }
            } else {
                throw new InsufficientAuthenticationException("Token mast start with " + TOKEN_HEADER);
            }

        } else {
            throw new InsufficientAuthenticationException("Token is Empty");
        }
    }

    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }

    protected boolean permissiveRequest(HttpServletRequest request) {
        if(permissiveRequestMatchers == null)
            return false;
        for(RequestMatcher permissiveMatcher : permissiveRequestMatchers) {
            if(permissiveMatcher.matches(request))
                return true;
        }
        return false;
    }

    public void setPermissiveUrl(String... urls) {
        if(permissiveRequestMatchers == null)
            permissiveRequestMatchers = new ArrayList<>();
        for(String url : urls)
            permissiveRequestMatchers .add(new AntPathRequestMatcher(url));
    }


    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException{
        SecurityContextHolder.getContext().setAuthentication(authResult);
        if (successHandler != null) {
            successHandler.onAuthenticationSuccess(request, response, authResult);
        }
    }

    public void setAuthenticationSuccessHandler(
            AuthenticationSuccessHandler successHandler) {
        Assert.notNull(successHandler, "successHandler cannot be null");
        this.successHandler = successHandler;
    }

    public void setAuthenticationFailureHandler(
            AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

}
