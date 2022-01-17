package com.sang.common.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.sang.common.filter.TokenAuthenticationFilter;
import com.sang.common.handle.EntryPointUnauthorizedHandler;
import com.sang.common.handle.RestAccessDeniedHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

/**
 * @author hxy
 * @date 2022/1/7 17:32
 **/
@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Value("${jwt.public.key}")
    private RSAPublicKey key;

    @Value("${jwt.private.key}")
    private RSAPrivateKey priv;

    private UserDetailsService userDetailsService;
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    private RestAccessDeniedHandler restAccessDeniedHandler;
    private PasswordEncoder passwordEncoder;

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
                .antMatchers("/**","/token").permitAll() //白名单
                .anyRequest().authenticated()// 除上面外的所有请求全部需要鉴权认证
                //自定义决策管理器accessDecisionManager，使用自定义AccessDecisionVoter
                .accessDecisionManager(accessDecisionManager())
                .and().cors().disable()
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(entryPointUnauthorizedHandler);

        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    public AccessDecisionManager accessDecisionManager(){
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
//                new MyExpressionVoter(), // 自定义授权访问
                new WebExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter());
        return new UnanimousBased(decisionVoters);
    }

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
