package com.boardProject.fastcampusprojectboard.config;

import com.boardProject.fastcampusprojectboard.dto.UserAccountDto;
import com.boardProject.fastcampusprojectboard.repository.UserAccountRepository;
import com.boardProject.fastcampusprojectboard.dto.security.BoardPrincipal;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//스프링 시큐리티 때문에, 맨 처음 페이지가 로그인 페이지라 안넘어가게됨, 넘어가게 하기 위해서 이 코드를 짬

@Configuration
public class SecurityConfig {

    //인증과 권한 체크
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .mvcMatchers(
                                /*POST 삭제, 수정, 추가에 대해서는 권한 체크*/
                                HttpMethod.GET,
                                "/", /*루트페이지*/
                                "/articles",
                                "/articles/search-hashtag"
                        ).permitAll()
                        .anyRequest().authenticated() /*저 위에 페이지를 제외한 다른 페이지는 인증을 요구함*/
                )
                .formLogin()
                        .and()
                .logout()
                        .logoutSuccessUrl("/") /*로그아웃하면 루트 페이지*/
                        .and()
                .build();

    }



    //사용자 정보 불러오기
    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository){
        return username -> userAccountRepository
                .findById(username)
                .map(UserAccountDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(()->new UsernameNotFoundException("유저를 찾을 수 없습니다 - username : "+username)); /*인증된 사용자를 찾지 못 했을 때의 대안*/

    }


    //암호화 모듈
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
