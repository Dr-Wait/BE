package com.DrWait.core.security.token;

import com.DrWait.core.env.EnvLoader;
import com.DrWait.domain.user.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;


/* 🔒 토큰 생성, 검증 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private SecretKey secretKey;
    private long accessTokenValidityInSeconds;
    private long refreshTokenValidityInSeconds;
    private static final String BEARER_PREFIX = "Bearer ";

    private final CustomUserDetailsService userDetailsService;

    @PostConstruct
    public void init() {
        log.info("[init] JwtTokenProvider에서 SecretKey 초기화 시작");

        String secret = EnvLoader.get("JWT_SECRET_KEY");
        this.accessTokenValidityInSeconds = EnvLoader.getInt("JWT_ACCESS_TOKEN_VALIDITY_IN_SECONDS", 3600);
        this.refreshTokenValidityInSeconds = EnvLoader.getInt("JWT_REFRESH_TOKEN_VALIDITY_IN_SECONDS", 4000);

        byte[] keyBytes = Base64.getEncoder()
                .encode(secret.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);


        log.info("[init] JwtTokenProvider에서 SecretKey 초기화 완료");
    }


    // ✅ AccessToken 생성
    public String generateAccessToken(String userId, String role){
        log.info("[generateAccessToken] 토큰 생성 시작");

        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenValidityInSeconds * 1000);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명 알고리즘
                .compact();

        log.info("[generateAccessToken] 만료시간: {}", expiry);
        return token;
    }

    // ✅ RefreshToken 생성
    public String generateRefreshToken(String userId, String role){
        log.info("[generateRefreshToken] 토큰 생성 시작");

        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + refreshTokenValidityInSeconds * 1000);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        log.info("[generateRefreshToken] 만료시간: {}", expiry);
        return token;
    }

    // ✅ 토큰으로 사용자 userId 추출
    public String getUserId(String token){
        log.info("[getUserEmail] 토큰 인증 이메일 정보 추출");
        return parseClaims(token).getSubject();
    }

    // ✅ 토큰으로 Authentication 객체를 생성해 반환
    public Authentication getAuthentication(String token){
        log.info("[getAuthentication] 토큰 인증 회원 정보 추출");

        Claims claims = parseClaims(token);
        String userId = claims.getSubject();
        String role = claims.get("role", String.class);

        String compositeKey = role + ":" + userId;
        UserDetails userDetails = userDetailsService.loadUserByUsername(compositeKey);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // ✅ 토큰 유효성 검사
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
        } catch (JwtException | IllegalArgumentException e){
            log.warn("유효하지 않은 JWT 토큰입니다.");
        }

        return false;
    }

    // ✅ HTTP 헤더에서 토큰 추출
    public String resolveToken(HttpServletRequest request){
        String bearer = request.getHeader("Authorization");

        if(bearer != null && bearer.startsWith(BEARER_PREFIX)){
            return bearer.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    private Claims parseClaims(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}