package com.higgsup.base.service;

import com.higgsup.base.entity.UserToken;

public interface IUserTokenService {
  void delete(Long userId);

  void save(UserToken userToken);
}
