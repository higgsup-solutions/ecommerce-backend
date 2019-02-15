package com.higgsup.base.service.impl;

import com.higgsup.base.entity.UserRole;
import com.higgsup.base.repository.UserRoleRepository;
import com.higgsup.base.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserRoleService implements IUserRoleService {

  private final UserRoleRepository userRoleRepository;

  public UserRoleService(
      UserRoleRepository userRoleRepository) {
    this.userRoleRepository = userRoleRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
  public void create(UserRole userRole) {
    userRoleRepository.save(userRole);
  }
}
