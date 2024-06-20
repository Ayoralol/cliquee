package cliquee.backend.controller;

import cliquee.backend.model.AuditLog;
import cliquee.backend.model.Group;
import cliquee.backend.model.User;
import cliquee.backend.service.AdminService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private AdminService adminService;

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers(@RequestParam UUID adminId) {
    List<User> users = adminService.getAllUsers(adminId);
    return ResponseEntity.ok(users);
  }

  @GetMapping("/groups")
  public ResponseEntity<List<Group>> getAllGroups(@RequestParam UUID adminId) {
    List<Group> groups = adminService.getAllGroups(adminId);
    return ResponseEntity.ok(groups);
  }

  @GetMapping("/logs")
  public ResponseEntity<List<AuditLog>> getAllLogs(@RequestParam UUID adminId) {
    List<AuditLog> logs = adminService.getAllLogs(adminId);
    return ResponseEntity.ok(logs);
  }

  @DeleteMapping("/logs/clear")
  public ResponseEntity<Void> clearLogs(@RequestParam UUID adminId) {
    adminService.clearLogs(adminId);
    return ResponseEntity.noContent().build();
  }
}
