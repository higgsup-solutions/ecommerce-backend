package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.ErrorCode;
import com.higgsup.xshop.common.UserRole;
import com.higgsup.xshop.common.WebUtil;
import com.higgsup.xshop.dto.UserDTO;
import com.higgsup.xshop.entity.Role;
import com.higgsup.xshop.entity.User;
import com.higgsup.xshop.exception.BusinessException;
import com.higgsup.xshop.repository.UserRepository;
import com.higgsup.xshop.security.model.UserContext;
import com.higgsup.xshop.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder encoder;

  public UserService(UserRepository userRepository,
      BCryptPasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.encoder = encoder;
  }

  @Override
  @Transactional(readOnly = true)
  public User getByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public User register(User user) {
    User result;
    Role userRole = new Role();
    userRole.setId(1);
    userRole.setRole(UserRole.MEMBER);
    user.setRole(userRole);
    user.setPassword(encoder.encode(user.getPassword()));
    User oldUser = userRepository.findByEmail(user.getEmail());

    if (oldUser != null) {
      throw new BusinessException(ErrorCode.USER_EMAIL_EXISTED,
          ErrorCode.USER_EMAIL_EXISTED.getErrorMessage());
    }

    try {
      result = userRepository.save(user);
    } catch (Exception e) {
      throw new BusinessException(ErrorCode.GLOBAL, "Cannot create new user");
    }
    return result;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDTO getCurrentUserInfo() {
    UserContext userContext = WebUtil.getCurrentUser();
    User user = userRepository.getOne(userContext.getUserId());
    UserDTO userDTO = new UserDTO();
    BeanUtils.copyProperties(user, userDTO);
    return userDTO;
  }
}
