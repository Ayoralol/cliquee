package cliquee.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/login", "/register")
          .permitAll()
          .requestMatchers("/admin/**")
          .hasRole("ADMIN")
          .anyRequest()
          .authenticated()
      )
      .addFilterBefore(
        new JsonUsernamePasswordAuthenticationFilter(
          authenticationManager(
            http.getSharedObject(AuthenticationConfiguration.class)
          )
        ),
        UsernamePasswordAuthenticationFilter.class
      )
      .csrf(csrf -> csrf.disable()); // Enable during production

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User
      .builder()
      .username("user")
      .password(passwordEncoder().encode("password"))
      .roles("USER")
      .build();
    UserDetails admin = User
      .builder()
      .username("admin")
      .password(passwordEncoder().encode("admin"))
      .roles("ADMIN")
      .build();
    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
