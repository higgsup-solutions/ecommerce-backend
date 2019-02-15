package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.entity.UserRole;
import com.higgsup.xshop.repository.UserRoleRepository;
import com.higgsup.xshop.service.IUserRoleService;
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
