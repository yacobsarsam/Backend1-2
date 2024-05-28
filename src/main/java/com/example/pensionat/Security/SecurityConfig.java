package com.example.pensionat.Security;

import com.example.pensionat.Security.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
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
                        .requestMatchers("/style.css","/register", "/login", "/forgot-password", "/reset-password", "/error", "/404").permitAll()
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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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
