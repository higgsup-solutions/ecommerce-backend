package com.higgsup.xshop.service;

import com.higgsup.xshop.entity.UserToken;

public interface IUserTokenService {
  void delete(Long userId);

  void save(UserToken userToken);
}
