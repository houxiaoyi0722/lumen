package com.sang.system.controller.auth;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.sang.common.constants.AuthConst;
import com.sang.common.constants.StringConst;
import com.sang.common.domain.auth.authentication.token.dto.TokenDto;
import com.sang.common.response.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.sang.common.constants.AuthConst.*;

/**
 * token controller.
 */
@RestController
public class TokenController {

	@Value("${jwt.expire}")
	public long EXPIRY;

	@Value("${jwt.refresh-expiry}")
	public long REFRESH_EXPIRY;

	@Resource
	private JwtEncoder encoder;

	@Resource
	private JwtDecoder decoder;

	@Resource
	private RedisTemplate<String, String> redisTemplate;

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

	@PostMapping("/authorizations")
	public Result<String> refreshToken(@RequestHeader String authorization) {
		authorization = authorization.replace(TOKEN_HEADER, StringConst.EMPTY);

		// jwt过期刷新
		Jwt freshToken = getFreshToken(Instant.now(), authorization);

		redisTemplate.boundValueOps(AuthConst.TOKEN_JWT + freshToken.getSubject()).set(freshToken.getTokenValue(), EXPIRY, TimeUnit.SECONDS);

		return Result.ok(freshToken.getTokenValue());
	}

	private Jwt getFreshToken(Instant now, String tokenDto) {
		Jwt refreshToken = decoder.decode(tokenDto);

		JwtClaimsSet freshClaims = JwtClaimsSet.builder()
				.id(refreshToken.getId())
				.issuedAt(now)
				.expiresAt(now.plusSeconds(EXPIRY))
				.subject(refreshToken.getSubject())
				.claim(ROLES, refreshToken.getClaim(ROLES))
				.build();
		return encoder.encode(JwtEncoderParameters.from(freshClaims));
	}
}
