package com.authserver.authorization;

import com.authserver.model.User;
import com.authserver.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.
        HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.
        EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().disable())
                .formLogin()
                .and().build();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> userRepo.findByUsername(username);
    }

    @Bean
    public ApplicationRunner dataLoader(
            UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            repo.save(
                    new User("habuma", encoder.encode("password"), "ROLE_ADMIN"));
            repo.save(
                    new User("tacochef", encoder.encode("password"), "ROLE_ADMIN"));
        };
    }
}