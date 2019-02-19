package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.repository.RoleRepository;
import com.higgsup.xshop.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoleService implements IRoleService {

  private final RoleRepository roleRepository;

  public RoleService(
      RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

}
