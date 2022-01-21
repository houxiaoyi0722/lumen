package com.sang.common.handle;

import cn.hutool.json.JSONUtil;
import com.sang.common.response.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hxy
 * @date 2022/1/17 17:46
 **/
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JSONUtil.toJsonStr(Result.error(e.getMessage(),HttpStatus.UNAUTHORIZED.value())));

    }

}
