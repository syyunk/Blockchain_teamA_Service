package com.ateam.popserver.common.config;

import com.ateam.popserver.common.utils.AuthFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthFailureHandler authFailureHandler;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/**").permitAll() // 로그인 권한은 누구나, resources파일도 모든권한
                .and()
                .formLogin()
                .loginPage("/member/login")
                .usernameParameter("mid")		// 로그인 시 form에서 가져올 값(id, email 등이 해당)
                .passwordParameter("pw")		// 로그인 시 form에서 가져올 값
                .defaultSuccessUrl("/main")
                .failureHandler(authFailureHandler)
                .and()
                .logout()
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/main")
                .and()
                .csrf().disable();		//로그인 창

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    
    @Bean
    public HttpFirewall defaultHttpFirewall() {
    	return new DefaultHttpFirewall();
    }
    
}
