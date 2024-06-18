package cliquee.backend.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JsonUsernamePasswordAuthenticationFilter
  extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public JsonUsernamePasswordAuthenticationFilter(
    AuthenticationManager authenticationManager
  ) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws AuthenticationException {
    try {
      // Log request method and headers
      System.out.println("Request Method: " + request.getMethod());
      System.out.println("Request Headers: ");
      request
        .getHeaderNames()
        .asIterator()
        .forEachRemaining(header ->
          System.out.println(header + ": " + request.getHeader(header))
        );

      StringBuilder sb = new StringBuilder();
      BufferedReader reader = request.getReader();
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }

      String requestBody = sb.toString();
      System.out.println("Request Body: " + requestBody); // Log the request body

      if (requestBody.isEmpty()) {
        throw new RuntimeException("Request body is empty");
      }

      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, String> loginData = objectMapper.readValue(
        requestBody,
        new TypeReference<HashMap<String, String>>() {}
      );

      String username = loginData.get("username");
      String password = loginData.get("password");

      if (username == null || password == null) {
        throw new RuntimeException("Username or Password is empty");
      }

      UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
        username,
        password
      );
      return authenticationManager.authenticate(authRequest);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
