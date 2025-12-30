package dev.nelon.dreamshops.security.jwt;

import dev.nelon.dreamshops.security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
	
	@Value("${auth.token.jwtSecret}")
	private String jwtSecret;
	
	@Value("${auth.token.expirationInMils}")
	private int expirationTime;
	
	public String generateTokenForUser(Authentication authentication) {
		ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();
		
		List<String> roles = userPrincipal.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.toList();
		
		Instant now = Instant.now();
		
		return Jwts.builder()
			.subject(userPrincipal.getEmail())
			.claim("id", userPrincipal.getId())
			.claim("roles", roles)
			.issuedAt(Date.from(now))
			.expiration(Date.from(now.plusMillis(expirationTime)))
			.signWith(key())
			.compact();
	}
	
	private SecretKey key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parser()
			.verifyWith(key())
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(key())
				.build()
				.parseSignedClaims(token)
				.getPayload();
			return true;
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
		         IllegalArgumentException e) {
			throw new JwtException(e.getMessage());
		}
	}
}
