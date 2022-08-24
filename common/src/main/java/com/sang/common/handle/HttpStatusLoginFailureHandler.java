package com.sang.common.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sang.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class HttpStatusLoginFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(e.getMessage(),HttpStatus.UNAUTHORIZED.value())));
		log.error(e.getMessage(),e);
	}

}
