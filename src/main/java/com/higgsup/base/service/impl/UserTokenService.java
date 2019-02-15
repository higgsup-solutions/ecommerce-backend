package com.higgsup.base.service.impl;

import com.higgsup.base.entity.UserToken;
import com.higgsup.base.repository.UserTokenRepository;
import com.higgsup.base.service.IUserTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserTokenService implements IUserTokenService {
  private final UserTokenRepository userTokenRepository;

  public UserTokenService(
      UserTokenRepository userTokenRepository) {
    this.userTokenRepository = userTokenRepository;
  }

  @Override
  public void delete(Long userId) {
    userTokenRepository.deleteById(userId);
  }

  @Override
  public void save(UserToken userToken) {
    userTokenRepository.save(userToken);
  }
}
