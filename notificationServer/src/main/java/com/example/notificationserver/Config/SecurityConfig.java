package com.example.notificationserver.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/ws/**").permitAll() // WebSocket 엔드포인트 접근 허용
                                .requestMatchers("/**").permitAll()   // 모든 요청에 대해 접근 허용 (실제 배포 시에는 더 세분화된 권한 설정 필요)
                                .anyRequest().authenticated()         // 나머지 요청은 인증된 사용자만 접근 가능
                )
                .formLogin(withDefaults()) // 로그인 설정
                .logout(withDefaults())   // 로그아웃 설정
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .cors(withDefaults()); // CORS 설정
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // 프론트엔드 주소
        configuration.setAllowedOrigins(Collections.singletonList("http://127.0.0.1:3000")); // 프론트엔드 주소
        //configuration.setAllowedOrigins(Collections.singletonList("http://192.168.0.8:3000")); // 프론트엔드 주소
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
