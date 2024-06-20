package cliquee.backend.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler
  extends SavedRequestAwareAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(
    HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication
  ) throws ServletException, IOException {
    boolean isAdmin = authentication
      .getAuthorities()
      .stream()
      .anyMatch(grantedAuthority ->
        grantedAuthority.getAuthority().equals("ROLE_ADMIN")
      );
    if (isAdmin) {
      setDefaultTargetUrl("/home");
    } else {
      setDefaultTargetUrl("/home");
    }
    super.onAuthenticationSuccess(request, response, authentication);
  }
}
