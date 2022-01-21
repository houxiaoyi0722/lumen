package com.sang.common.config.auth;

import com.sang.common.filter.JwtTokenAuthenticationFilter;
import com.sang.common.handle.HttpStatusLoginFailureHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

public class JwtTokenLoginConfigurer<T extends JwtTokenLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

	private JwtTokenAuthenticationFilter authFilter;

	public JwtTokenLoginConfigurer() {
		this.authFilter = new JwtTokenAuthenticationFilter();
	}

	@Override
	public void configure(B http) {
		authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		authFilter.setAuthenticationFailureHandler(new HttpStatusLoginFailureHandler());

		JwtTokenAuthenticationFilter filter = postProcess(authFilter);
		http.addFilterBefore(filter, LogoutFilter.class);
	}

	public JwtTokenLoginConfigurer<T, B> permissiveRequestUrls(String ... urls){
		authFilter.setPermissiveUrl(urls);
		return this;
	}

//	public JwtTokenLoginConfigurer<T, B> tokenValidSuccessHandler(AuthenticationSuccessHandler successHandler){
//		authFilter.setAuthenticationSuccessHandler(successHandler);
//		return this;
//	}

}
