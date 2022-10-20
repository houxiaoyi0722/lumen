package com.sang.common.config.auth;

import com.sang.common.domain.auth.authorization.token.dto.TokenDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author hxy
 * @date 2022/1/21 17:12
 **/
@Getter
@Setter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 2345484165456489645L;

    private UserDetails principal;
    private String credentials;
    private String token;

    public JwtAuthenticationToken(UserDetails principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
    }

    public JwtAuthenticationToken(String token) {
        super(Collections.emptyList());
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
