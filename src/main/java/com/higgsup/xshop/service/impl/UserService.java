package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.ErrorCode;
import com.higgsup.xshop.dto.UserDTO;
import com.higgsup.xshop.entity.Role;
import com.higgsup.xshop.entity.User;
import com.higgsup.xshop.entity.UserRole;
import com.higgsup.xshop.exception.BusinessException;
import com.higgsup.xshop.repository.UserRepository;
import com.higgsup.xshop.repository.UserRoleRepository;
import com.higgsup.xshop.service.IUserRoleService;
import com.higgsup.xshop.service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final UserRoleRepository userRoleRepository;

  private final IUserRoleService userRoleService;


  public UserService(UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      UserRoleRepository userRoleRepository,
      IUserRoleService userRoleService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userRoleRepository = userRoleRepository;
    this.userRoleService = userRoleService;
  }

  @Override
  public User getByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public List<User> getUser() {
    return userRepository.findAll();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createUser(UserDTO userDTO) {
    User user = new User();
    user.setId(userDTO.getId());
    user.setUsername(userDTO.getUserName());
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    List<UserRole> roles = new ArrayList<>();
    userRepository.save(user);
    UserRole userRole = new UserRole();
    userRole.setRole(Role.MEMBER);
    userRole.setUserId(user.getId());
    roles.add(userRole);
    userRoleRepository.saveAll(roles);
    throw new BusinessException(ErrorCode.GLOBAL, "Test business exception...");
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createUserDemoTransaction(UserDTO userDTO) {
    User user = new User();
    user.setId(userDTO.getId());
    user.setUsername(userDTO.getUserName());
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    userRepository.save(user);
    UserRole userRole = new UserRole();
    userRole.setRole(Role.MEMBER);
    userRole.setUserId(user.getId());
    userRoleService.create(userRole);
  }
}
