package com.higgsup.xshop.service;

import com.higgsup.xshop.common.TokenType;
import com.higgsup.xshop.entity.UserToken;

import java.util.List;

public interface IUserTokenService {
  void deleteByUserId(Integer userId);

  void saveAll(List<UserToken> userToken);

  void save(UserToken userToken);

  UserToken findByUserIdAndType(Integer userId, TokenType tokenType);
}
