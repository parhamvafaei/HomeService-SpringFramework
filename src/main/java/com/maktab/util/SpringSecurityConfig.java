package com.maktab.util;

import com.maktab.exception.NotFoundPersonException;
import com.maktab.service.userdetailserviceimpl.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


    private final PasswordEncoder passwordEncoder;
    private final PersonDetailService detailService;

    public SpringSecurityConfig( PersonDetailService detailService , PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.detailService = detailService;
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http
            .csrf().disable()
            .authorizeHttpRequests()
            .antMatchers("/api/v1/client/save-client").permitAll()
            .antMatchers("/api/v1/expert/save-expert").permitAll()
            .antMatchers("/api/v1/admin/expert-confirm").permitAll()
            .antMatchers("/api/v1/admin/client-confirm").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/expert/**").hasRole("EXPERT")
            .antMatchers("/client/**").hasRole("CLIENT")
            .anyRequest()
            .authenticated()
            .and().httpBasic();
    return http.build();
}


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((username -> {
            try {
                return detailService
                        .loadUserByUsername(username);
            } catch (Throwable e) {
                throw new NotFoundPersonException("Email/Password is wrong");
            }
        })).passwordEncoder(passwordEncoder);
    }




}
