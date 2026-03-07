package org.example.concert_booking.SecurityConfig;

import org.example.concert_booking.Security.ApiAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//import org.example.concert_booking.Service.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class Security {


//    @Autowired
//    CustomUserDetailsService customUserDetailsService;

    @Autowired
    ApiAuthenticationFilter apiAuthenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(c -> c.disable())
                .authorizeHttpRequests(request -> request

                        // ADMIN endpoints FIRST
                        .requestMatchers("/api/add_concert","/api/editConcert/**","/api/deleteConcert/**")
                        .hasRole("ADMIN")

                        // public endpoints
                        .requestMatchers("/registration","/css/**","/js/**","/api/login","/api/register",
                                "/swagger-ui/**","/v3/api-docs/**","/swagger-ui.html")
                        .permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(apiAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)                .formLogin(form -> form.disable())
                .logout(form -> form.disable());

        return http.build();
    }

//    @Autowired
//    public void configure (AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//    }
}