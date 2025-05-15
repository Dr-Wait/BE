package com.DrWait.global.security.jwt;

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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;


/* 🔒 토큰 생성, 검증 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private Key secretKey;
    private static final String BEARER_PREFIX = "Bearar ";

    @PostConstruct
    protected void init(){
        log.info("[init] JwtTokenProvider에서 SecretKey 초기화 시작");

        // secret key를 base64 형식으로 인코딩해 암호화
        byte[] keyBytes = Base64.getEncoder()
                .encode(jwtProperties.getSecretKey().getBytes());
        secretKey = Keys.hmacShaKeyFor(keyBytes);

        log.info("[init] JwtTokenProvider에서 SecretKey 초기화 완료");
    }

    // ✅ AccessToken 생성
    public String generateAccessToken(String email, String role){
        log.info("[generateAccessToken] 토큰 생성 시작");

        Claims claims = Jwts.claims();
        claims.put("role", role); // 토큰을 사용하는 사용자의 권한을 확인용 role 값 별개 추가

        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getAccessTokenValidityInSeconds() * 1000);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // 이메일를 고유 식별자로 사용
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명 알고리즘
                .compact();

        log.info("[generateAccessToken] 토큰 생성 완료");
        return token;
    }

    // ✅ 토큰으로 사용자 email 추출
    public String getUserEmail(String token){
        log.info("[getUserEmail] 토큰 인증 이메일 정보 추출");
        return parseClaims(token).getSubject();
    }

    // ✅ 토큰으로 Authentication 객체를 생성해 반환
    public Authentication getAuthentication(String token){
        log.info("[getAuthentication] 토큰 인증 회원 정보 추출");

        Claims claims = parseClaims(token);
        String email = claims.getSubject();
        String role = claims.get("role", String.class);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        // 여기 User는 Entity ❌ springframework.security.core.userdetails ⭕
        UserDetails userDetails = new User(email, "", authorities);

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