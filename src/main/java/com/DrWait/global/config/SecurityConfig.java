package com.DrWait.global.config;

import com.DrWait.domain.user.service.CustomUserDetailsService;
import com.DrWait.global.security.token.JwtAccessDeniedHandler;
import com.DrWait.global.security.token.JwtAuthenticationEntryPoint;
import com.DrWait.global.security.token.JwtAuthenticationFilter;
import com.DrWait.global.security.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties // JwtProperties 등록을 위해 필요
@EnableWebSecurity // 스프링 시큐리티의 기본 설정을 비활성화하고, 사용자 정의 보안 설정을 활성화
@EnableMethodSecurity(prePostEnabled = true)
// @PreAuthorize, @PostAuthorize 같은 메서드 수준 보안 어노테이션을 활성화
// → 컨트롤러, 서비스 등의 메서드에 @PreAuthorize("hasRole('ROLE_ADMIN')") 같은 권한 체크를 사용하려면 필요
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;
  private final CustomUserDetailsService userDetailsService;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/**") // 선택사항
        .csrf(csrf -> csrf.disable())
        .httpBasic(httpBasic -> httpBasic.disable())
        .formLogin(form -> form.disable())
        .logout(logout -> logout.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/signup/**").permitAll()
            .requestMatchers("/api/auth/login/**").permitAll()
            .requestMatchers("/api/auth/hospital/signup/**").permitAll()
            .requestMatchers("/api/auth/hospital/login/**").permitAll()
            .requestMatchers("/api/reservation/**").permitAll()
            .requestMatchers("/api/management/**").permitAll()
            .requestMatchers("/api/recommend/**").permitAll()
            .requestMatchers("/api/hospital/**").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService),
            UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
        );

    return http.build();
  }


  // ✅ 비밀번호 암호화
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // ✅ 로그인 시 인증에 사용되는 매니저
  @Bean
  public AuthenticationManager authenticationManger(
      AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

}