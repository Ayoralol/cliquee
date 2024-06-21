package cliquee.backend.service;

import cliquee.backend.model.AuditLog;
import cliquee.backend.model.Group;
import cliquee.backend.model.User;
import cliquee.backend.repository.AuditLogRepository;
import cliquee.backend.repository.GroupRepository;
import cliquee.backend.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private AuditLogRepository auditLogRepository;

  public List<User> getAllUsers(UUID adminId) {
    if (!confirmAdminId(adminId)) {
      throw new RuntimeException("Unauthorized");
    }
    return userRepository.findAll();
  }

  public List<Group> getAllGroups(UUID adminId) {
    if (!confirmAdminId(adminId)) {
      throw new RuntimeException("Unauthorized");
    }
    return groupRepository.findAll();
  }

  public List<AuditLog> getAllLogs(UUID adminId) {
    if (!confirmAdminId(adminId)) {
      throw new RuntimeException("Unauthorized");
    }
    return auditLogRepository.findAll();
  }

  public Void clearLogs(UUID adminId) {
    if (!confirmAdminId(adminId)) {
      throw new RuntimeException("Unauthorized");
    }
    auditLogRepository.deleteAll();
    return null;
  }

  public Boolean confirmAdminId(UUID adminId) {
    User user = userRepository.findById(adminId).orElse(null);
    return user != null && user.getRole().split(",")[0].equals("ADMIN");
  }
}
