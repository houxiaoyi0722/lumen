package com.sang.common.config.auth;

import com.sang.common.filter.JsonUsernamePasswordAuthenticationFilter;
import com.sang.common.handle.HttpStatusLoginFailureHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

/**
 * @author hxy
 * @date 2022/1/20 11:36
 **/
public class JsonLoginConfigurer<T extends JsonLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

    private JsonUsernamePasswordAuthenticationFilter authFilter;

    public JsonLoginConfigurer(String pattern, String httpMethod) {
        this.authFilter = new JsonUsernamePasswordAuthenticationFilter(pattern, httpMethod);
    }

    @Override
    public void configure(B builder) throws Exception {
        authFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        authFilter.setAuthenticationFailureHandler(new HttpStatusLoginFailureHandler());
        authFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());

        JsonUsernamePasswordAuthenticationFilter filter = postProcess(authFilter);
        builder.addFilterAfter(filter, LogoutFilter.class);
    }

    public JsonLoginConfigurer<T,B> loginSuccessHandler(AuthenticationSuccessHandler authSuccessHandler){
        authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
        return this;
    }

}
