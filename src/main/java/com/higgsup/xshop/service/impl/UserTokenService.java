package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.TokenType;
import com.higgsup.xshop.entity.UserToken;
import com.higgsup.xshop.repository.UserTokenRepository;
import com.higgsup.xshop.service.IUserTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserTokenService implements IUserTokenService {
  private final UserTokenRepository userTokenRepository;

  public UserTokenService(
      UserTokenRepository userTokenRepository) {
    this.userTokenRepository = userTokenRepository;
  }

  @Override
  public void deleteByUserId(Integer userId) {
    userTokenRepository.deleteByUserId(userId);
  }

  @Override
  public void saveAll(List<UserToken> userTokens) {
    userTokenRepository.saveAll(userTokens);
  }

  @Override
  public void save(UserToken userToken) {
    userTokenRepository.save(userToken);
  }

  @Override
  public UserToken findByUserIdAndType(Integer userId, TokenType tokenType) {
    return userTokenRepository.findByUserIdAndType(userId, tokenType);
  }
}
