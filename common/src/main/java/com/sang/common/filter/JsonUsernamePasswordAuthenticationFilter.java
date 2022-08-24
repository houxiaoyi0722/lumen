package com.sang.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sang.common.constants.StringConst;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * @author hxy
 * @date 2022/1/19 16:56
 **/
public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public JsonUsernamePasswordAuthenticationFilter(String pattern, String httpMethod) {
        super(new AntPathRequestMatcher(pattern, httpMethod));
    }

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;

    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(getAuthenticationManager(), "authenticationManager must be specified");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        String username = null;
        String password = null;
        if (StringUtils.hasText(body)) {
            Map map = new ObjectMapper().readValue(body, Map.class);
            username = map.get(usernameParameter).toString();
            password = map.get(passwordParameter).toString();
        }
        username = Optional.ofNullable(username).orElse(StringConst.EMPTY).trim();
        password = Optional.ofNullable(password).orElse(StringConst.EMPTY);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


}
