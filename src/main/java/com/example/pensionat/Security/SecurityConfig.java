package com.example.pensionat.Security;

import com.example.pensionat.Security.Services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/admin/users","/addUser","/company", "/login", "/rum/allRooms", "/blacklist").hasRole("ADMIN")
                        .requestMatchers("/book", "/kunder").hasRole("RECEPTIONIST")
                        .requestMatchers("/style.css","/register", "/login", "/forgot-password", "/reset-password").permitAll()
                        .anyRequest().authenticated()
                )
//                                .oauth2Login(oauth2 -> oauth2
//                        .userInfoEndpoint(userInfo -> userInfo.userAuthoritiesMapper(userAuthoritiesMapper()))
//                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

//    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return authorities -> {
//            List<SimpleGrantedAuthority> mappedAuthorities = new ArrayList<>();
//            authorities.forEach(authority -> {
//                if (authority instanceof OAuth2UserAuthority oauth2UserAuthority) {
//                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
//                    String login = (String) userAttributes.get("login");
//                    if ("aspcodenet".equals(login)) {
//                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//                    }
//                }
//            });
//            return mappedAuthorities;
//        };
//    }
}
