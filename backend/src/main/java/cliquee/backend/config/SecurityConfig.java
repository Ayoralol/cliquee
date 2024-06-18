package cliquee.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/auth/login", "/register")
          .permitAll()
          .requestMatchers("/admin/**")
          .hasRole("ADMIN")
          .anyRequest()
          .authenticated()
      )
      .csrf(csrf -> csrf.disable()); // Disable CSRF temporarily for testing
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(
      User
        .withUsername("user")
        .password(passwordEncoder().encode("password"))
        .roles("USER")
        .build(),
      User
        .withUsername("admin")
        .password(passwordEncoder().encode("admin"))
        .roles("ADMIN")
        .build()
    );
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http)
    throws Exception {
    AuthenticationManagerBuilder auth = http.getSharedObject(
      AuthenticationManagerBuilder.class
    );
    auth
      .userDetailsService(userDetailsService())
      .passwordEncoder(passwordEncoder());
    return auth.build();
  }
}
