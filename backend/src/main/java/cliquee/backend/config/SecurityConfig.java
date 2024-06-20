package cliquee.backend.config;

import cliquee.backend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//   @Bean
//   public SecurityFilterChain filterChain(
//     HttpSecurity http,
//     AuthenticationConfiguration authenticationConfiguration
//   ) throws Exception {
//     JsonUsernamePasswordAuthenticationFilter customFilter = new JsonUsernamePasswordAuthenticationFilter(
//       authenticationManager(authenticationConfiguration)
//     );

//     http
//       .authorizeHttpRequests(auth ->
//         auth
//           .requestMatchers("/login", "/register")
//           .permitAll()
//           .requestMatchers("/admin/**")
//           .hasRole("ADMIN")
//           .anyRequest()
//           .authenticated()
//       )
//       .formLogin(formLogin ->
//         formLogin.loginPage("/login").defaultSuccessUrl("/", true).permitAll()
//       )
//       .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
//       .httpBasic(withDefaults -> {}) // Enable basic auth temporarily
//       .csrf(csrf -> csrf.disable()); // Disable CSRF temporarily for testing

//     http.addFilterBefore(
//       customFilter,
//       UsernamePasswordAuthenticationFilter.class
//     );

//     return http.build();
//   }

//   @Bean
//   public AuthenticationManager authenticationManager(
//     AuthenticationConfiguration authenticationConfiguration
//   ) throws Exception {
//     return authenticationConfiguration.getAuthenticationManager();
//   }

//   @Bean
//   public UserDetailsService userDetailsService() {
//     UserDetails user = User
//       .builder()
//       .username("user")
//       .password(passwordEncoder().encode("password"))
//       .roles("USER")
//       .build();
//     UserDetails admin = User
//       .builder()
//       .username("admin")
//       .password(passwordEncoder().encode("admin"))
//       .roles("ADMIN")
//       .build();
//     return new InMemoryUserDetailsManager(user, admin);
//   }

//   @Bean
//   public PasswordEncoder passwordEncoder() {
//     return new BCryptPasswordEncoder();
//   }
// }

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    throws Exception {
    return httpSecurity
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(registry -> {
        registry.requestMatchers("/**").permitAll();
      })
      .formLogin(httpSecurityFormLoginConfigurer -> {
        httpSecurityFormLoginConfigurer
          .loginPage("/login")
          .successHandler(new AuthenticationSuccessHandler())
          .permitAll();
      })
      .build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return userDetailsService;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
