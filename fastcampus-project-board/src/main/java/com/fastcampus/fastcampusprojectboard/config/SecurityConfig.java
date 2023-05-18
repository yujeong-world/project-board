package com.fastcampus.fastcampusprojectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//스프링 시큐리티 때문에, 맨 처음 페이지가 로그인 페이지라 안넘어가게됨, 넘어가게 하기 위해서 이 코드를 짬

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .formLogin().and()
                .build();


    }
}
