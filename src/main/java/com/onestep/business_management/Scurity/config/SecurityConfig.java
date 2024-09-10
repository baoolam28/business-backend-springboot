//    package com.onestep.business_management.Scurity.config;
//
//
//    import lombok.RequiredArgsConstructor;
//    import org.springframework.context.annotation.Bean;
//    import org.springframework.context.annotation.Configuration;
//    import org.springframework.security.authentication.AuthenticationProvider;
//    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//    import org.springframework.security.config.http.SessionCreationPolicy;
//    import org.springframework.security.web.SecurityFilterChain;
//    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//    @Configuration
//    @EnableWebSecurity
//    @RequiredArgsConstructor
//    public class SecurityConfig {
//
//        private final JwtAuthenticationFilter jwtAuthFilter;
//        private final AuthenticationProvider authenticationProvider;
//
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//            http
//                    .csrf(csrf -> csrf.disable())
//                    .authorizeHttpRequests(auth -> auth
//                            .requestMatchers("/auth/**").permitAll()
//                            .requestMatchers("/create_account").hasAnyAuthority("create_account")
//                            .requestMatchers("/sales").hasAnyAuthority("sales")
//                            .anyRequest().authenticated()
//                    )
//                    .sessionManagement(session -> session
//                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    )
//                    .authenticationProvider(authenticationProvider)
//                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                    .formLogin(form -> form
//                            .loginPage("/login") // Trang đăng nhập tùy chỉnh
//                            .permitAll()
//                    )
//                    .exceptionHandling(exception -> exception
//                            .authenticationEntryPoint((request, response, authException) -> {
//                                response.sendRedirect("/login"); // Chuyển hướng về trang đăng nhập khi không xác thực
//                            })
//                    );
//
//            return http.build();
//        }
//    }
package com.onestep.business_management.Scurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Cho phép tất cả các yêu cầu mà không cần xác thực
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                        .loginPage("/login") // Trang đăng nhập tùy chỉnh
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/login"); // Chuyển hướng về trang đăng nhập khi không xác thực
                        })
                );

        return http.build();
    }
}
