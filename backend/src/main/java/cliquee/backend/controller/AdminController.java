package cliquee.backend.controller;

import cliquee.backend.model.AuditLog;
import cliquee.backend.model.Group;
import cliquee.backend.model.User;
import cliquee.backend.service.AdminService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private AdminService adminService;

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = adminService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/groups")
  public ResponseEntity<List<Group>> getAllGroups() {
    List<Group> groups = adminService.getAllGroups();
    return ResponseEntity.ok(groups);
  }

  @GetMapping("/logs")
  public ResponseEntity<List<AuditLog>> getAllLogs() {
    List<AuditLog> logs = adminService.getAllLogs();
    return ResponseEntity.ok(logs);
  }

  @DeleteMapping("/logs/clear")
  public ResponseEntity<Void> clearLogs() {
    adminService.clearLogs();
    return ResponseEntity.noContent().build();
  }
}
