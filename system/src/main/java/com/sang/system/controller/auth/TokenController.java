package com.sang.system.controller.auth;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.sang.common.domain.auth.authorization.token.dto.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sang.common.constants.AuthConst.*;

/**
 * A controller for the token resource.
 */
@RestController
public class TokenController {

	@Value("${jwt.expire}")
	public long EXPIRY;

	@Value("${jwt.refresh-expiry}")
	public long REFRESH_EXPIRY;

	@Resource
	private JwtEncoder encoder;

	/**
	 * 已废弃
	 * @param authentication
	 * @return
	 */
	@Deprecated
	@PostMapping("/login")
	public TokenDto token(Authentication authentication) {
		Instant now = Instant.now();
		// @formatter:off
		String scope = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(ROLE_SPLIT));

		String id = UUID.randomUUID().toString().replaceAll("-", "");

		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer(SELF)
				.id(id)
				.issuedAt(now)
				.expiresAt(now.plusSeconds(EXPIRY))
				.subject(authentication.getName())
				.claim(ROLES, scope)
				.build();

		JwtClaimsSet refreshClaims = JwtClaimsSet.builder()
				.issuer(SELF)
				.id(id)
				.issuedAt(now)
				.expiresAt(now.plusSeconds(REFRESH_EXPIRY))
				.subject(authentication.getName())
				.claim(ROLES, scope)
				.build();

		Jwt encode = this.encoder.encode(JwtEncoderParameters.from(claims));
		String tokenValue = encode.getTokenValue();
		String refreshTokenValue = this.encoder.encode(JwtEncoderParameters.from(refreshClaims)).getTokenValue();

		// @formatter:on
		return TokenDto.builder()
				.token(tokenValue)
				.refreshToken(refreshTokenValue)
				.tokenHead(TOKEN_HEADER)
				.expiresIn(DateUtil.between(Date.from(Objects.requireNonNull(encode.getExpiresAt())), DateUtil.date(), DateUnit.SECOND))
				.build();
	}

}
