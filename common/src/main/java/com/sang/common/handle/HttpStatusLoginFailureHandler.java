package com.sang.common.handle;

import cn.hutool.json.JSONUtil;
import com.sang.common.response.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpStatusLoginFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.getWriter().write(JSONUtil.toJsonStr(Result.error(e.getMessage(),HttpStatus.UNAUTHORIZED.value())));

	}

}
