package com.sang.common.handle;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.sang.common.constants.AuthConst.REFRESH_TOKEN_JWT;
import static com.sang.common.constants.AuthConst.TOKEN_JWT;

public class JwtTokenClearLogoutHandler implements LogoutHandler {

	@Resource
	private RedisTemplate<String,String> redisTemplate;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		clearToken(authentication);
	}

	protected void clearToken(Authentication authentication) {
		if(authentication == null)
			return;
		UserDetails user = (UserDetails)authentication.getPrincipal();

		if(user!=null && user.getUsername()!=null) {
			redisTemplate.delete(TOKEN_JWT + user.getUsername());
			redisTemplate.delete(REFRESH_TOKEN_JWT + user.getUsername());
		}
	}

}
