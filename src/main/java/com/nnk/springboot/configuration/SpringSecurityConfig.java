package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p>SpringSecurityConfig is an Entity that customizes Spring Security to our needs (Filters, Password Handling...)</p>
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * <p>A Bean that dictates how various HTTP Requests pass, how they interact with Roles, what filters to apply and how the Login page functions.</p>
     * @param http HttpSecurity Entity used to rebuild the request after being authorized and authenticated
     * @return a customized SecurityFilterChain Entity
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/admin").hasRole("ADMIN");
            auth.requestMatchers("/user").hasRole("USER");
            auth.anyRequest().authenticated();
        }).formLogin(formLogin -> formLogin
            .successHandler(new CustomAuthenticationSuccessHandler())
        ).oauth2Login(Customizer.withDefaults()).build();
    }

    /**
     * <p>A Bean used to dictate how the passwords should be encrypted.</p>
     * @return a BCryptPasswordEncoder Entity
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * <p>A Bean used to validate a Spring User</p>
     * @param http HttpSecurity Entity used to obtain an AuthenticationManagerBuilder Entity, used to obtain the User Details
     * @param bCryptPasswordEncoder the Password Encoder
     * @return an AuthenticationManager Entity with the User Details (authenticated)
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
            .getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
            .passwordEncoder(bCryptPasswordEncoder);
            
        return authenticationManagerBuilder.build();
    }
}
