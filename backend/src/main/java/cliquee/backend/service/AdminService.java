package cliquee.backend.service;

import cliquee.backend.model.AuditLog;
import cliquee.backend.model.Group;
import cliquee.backend.model.User;
import cliquee.backend.repository.AuditLogRepository;
import cliquee.backend.repository.GroupRepository;
import cliquee.backend.repository.UserRepository;
import java.util.List;
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

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public List<Group> getAllGroups() {
    return groupRepository.findAll();
  }

  public List<AuditLog> getAllLogs() {
    return auditLogRepository.findAll();
  }

  public Void clearLogs() {
    auditLogRepository.deleteAll();
    return null;
  }
}
