package com.sang.common.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sang.common.response.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hxy
 * @date 2022/1/17 17:45
 **/
@Slf4j
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Resource
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {

        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(objectMapper.writeValueAsString(Result.error(e.getMessage(),HttpStatus.FORBIDDEN.value())));
        log.error(e.getMessage(),e);
    }

}
