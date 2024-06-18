package cliquee.backend.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JsonUsernamePasswordAuthenticationFilter
  extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public JsonUsernamePasswordAuthenticationFilter(
    AuthenticationManager authenticationManager
  ) {
    this.authenticationManager = authenticationManager;
    setRequiresAuthenticationRequestMatcher(
      new AntPathRequestMatcher("/login", "POST")
    );
  }

  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws AuthenticationException {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, String> loginData = objectMapper.readValue(
        request.getInputStream(),
        new TypeReference<Map<String, String>>() {}
      );
      String username = loginData.get("username");
      String password = loginData.get("password");
      UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
        username,
        password
      );
      return authenticationManager.authenticate(authRequest);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain,
    Authentication authResult
  ) throws IOException {
    response.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  protected void unsuccessfulAuthentication(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException failed
  ) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }
}
