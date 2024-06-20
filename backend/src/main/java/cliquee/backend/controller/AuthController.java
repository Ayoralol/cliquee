// package cliquee.backend.controller;

// import cliquee.backend.model.User;
// import jakarta.servlet.http.HttpServletRequest;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class AuthController {

//   @Autowired
//   private AuthenticationManager authenticationManager;

//   @PostMapping("/login")
//   public String login(@RequestBody User user, HttpServletRequest request) {
//     try {
//       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//         user.getUsername(),
//         user.getPassword()
//       );
//       authenticationToken.setDetails(
//         new WebAuthenticationDetailsSource().buildDetails(request)
//       );
//       Authentication authentication = authenticationManager.authenticate(
//         authenticationToken
//       );
//       SecurityContextHolder.getContext().setAuthentication(authentication);
//       return "Login successful!";
//     } catch (AuthenticationException e) {
//       return "Login failed: " + e.getMessage();
//     }
//   }
// }
