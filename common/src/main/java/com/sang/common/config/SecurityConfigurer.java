package com.sang.common.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author hxy
 * @date 2022/1/7 17:32
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 允许对于网站静态资源的无授权访问
        web.ignoring().antMatchers("/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/swagger-ui.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/webjars/**",
                "/csrf",
                "/v1/healthz",
                "/actuator/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll() //放行所有健康检查请求
                .antMatchers(HttpMethod.OPTIONS).permitAll()//跨域请求会先进行一次options请求
                .antMatchers("/**").permitAll() //白名单
                .anyRequest().authenticated()// 除上面外的所有请求全部需要鉴权认证
                //自定义决策管理器accessDecisionManager，使用自定义AccessDecisionVoter
                //.accessDecisionManager(accessDecisionManager())
                .and().cors().disable();
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
//    }
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**/*.html", "/resources/**/*.js");
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .formLogin()
//                .loginPage("/login_page")
//                .passwordParameter("username")
//                .passwordParameter("password")
//                .loginProcessingUrl("/sign_in")
//                .permitAll()
//                .and().authorizeRequests().antMatchers("/test").hasRole("test")
//                .anyRequest().authenticated().accessDecisionManager(accessDecisionManager())
//                .and().logout().logoutSuccessHandler(new MyLogoutSuccessHandler())
//                .and().csrf().disable();
//        http.addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());
//        http.addFilterAfter(new MyFittler(), LogoutFilter.class);
//    }
}
