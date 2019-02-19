package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.entity.User;
import com.higgsup.xshop.repository.RoleRepository;
import com.higgsup.xshop.repository.UserRepository;
import com.higgsup.xshop.service.IRoleService;
import com.higgsup.xshop.service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final RoleRepository roleRepository;

  private final IRoleService userRoleService;


  public UserService(UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      RoleRepository roleRepository,
      IRoleService userRoleService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
    this.userRoleService = userRoleService;
  }

  @Override
  public User getByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }
}
