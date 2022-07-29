package com.sang.common.config.auth;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.sang.common.handle.EntryPointUnauthorizedHandler;
import com.sang.common.handle.JsonLoginSuccessHandler;
import com.sang.common.handle.JwtTokenClearLogoutHandler;
import com.sang.common.handle.RestAccessDeniedHandler;
import com.sang.common.provider.JwtTokenAuthenticationProvider;
import lombok.NoArgsConstructor;
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
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.annotation.Resource;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

/**
 * @author hxy
 * @date 2022/1/7 17:32
 **/
@NoArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Resource
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;

    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Resource
    private UserDetailsService userDetailsService;

    @Value("${jwt.public.key}")
    private RSAPublicKey key;

    @Value("${jwt.private.key}")
    private RSAPrivateKey priv;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin().disable();
        // 接口权限
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()//跨域请求会先进行一次options请求
                .antMatchers("/login","/**").permitAll() //白名单
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll() //放行所有健康检查请求
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                //自定义决策管理器accessDecisionManager，使用自定义AccessDecisionVoter
                .accessDecisionManager(accessDecisionManager());

        // session相关
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().disable() // cors由nginx实现
                .csrf().disable();// 关闭csrf，因为不使用session

        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(entryPointUnauthorizedHandler);


        // 禁用缓存
        http.headers().cacheControl();

        // 添加自定义jwt过滤器
        http.apply(new JwtTokenLoginConfigurer<>()).permissiveRequestUrls("/logout");
        http.apply(new JsonLoginConfigurer<>("/login",HttpMethod.POST.name())).loginSuccessHandler(jsonLoginSuccessHandler());

        // logout
        http.logout()
                .addLogoutHandler(tokenClearLogoutHandler())
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(authenticationProvider);
        auth.authenticationProvider(jwtTokenAuthenticationProvider());
    }

    @Bean
    protected JsonLoginSuccessHandler jsonLoginSuccessHandler() {
        return new JsonLoginSuccessHandler();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    protected AuthenticationProvider jwtTokenAuthenticationProvider() {
        return new JwtTokenAuthenticationProvider(jwtDecoder(),jwtEncoder());
    }

    @Bean
    protected JwtTokenClearLogoutHandler tokenClearLogoutHandler() {
        return new JwtTokenClearLogoutHandler();
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

}
