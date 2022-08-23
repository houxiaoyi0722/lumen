package com.sang.common.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sang.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hxy
 * @date 2022/1/17 17:46
 **/
@Slf4j
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(Result.error(e.getMessage(),HttpStatus.UNAUTHORIZED.value())));
        log.error(e.getMessage(),e);
    }

}
