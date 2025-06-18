package com.korit.authstudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean   // ioc의 부품으로 등록해줌
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 요청을 보내는 쪽의 도매인(사이트 주소)
        corsConfiguration.addAllowedOriginPattern(CorsConfiguration.ALL);
        // 요청을 보내는 쪽에서 Request, Response HEADER 정보에 대한 제약
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        // 요청시 보내는 쪽의 메소드(GET, POST, PUT, DELETE, OPTION 등)
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);


        // 요청 URL (/api/users)에 대한 CORS 설정 적용을 위해 객체생성
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean // 인터페이스는 어노테이션을 달지못해 클래스 객체를 생성해서 매개변수로 등록된 것들을 가지고
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());   // 위에서 만든 cors 설정(bean) security에 적용
        http.csrf(csrf -> csrf.disable());                  // 서버사이드 렌더링 방식이 아니니 REST API 방식에서는 비활성화
        http.formLogin(formLogin -> formLogin.disable());   //서버사이드 렌더링 로그인 방식 비활성화
        http.httpBasic(httpBasic -> httpBasic.disable());   //HTTP 프로토콜 기본 로그인 방식 비활성화
        http.logout(logout -> logout.disable());            // 서버사이드 렌더링 로그아웃 방식 비활성화

        // 특정 요청 URL에 대한 권한 설정
        //authorizeHttpRequests -> 인증할 요청 URL에 모든 요청을 받지않음
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        // HttpSecurity 객체에 설정한 모든 정보를 기반으로 bild하여 SecutirtyFilter 객체 생성 후 BEAN 등록
        return http.build();
    }
}
