package com.higgsup.xshop.repository;

import com.higgsup.xshop.common.TokenType;
import com.higgsup.xshop.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {

  UserToken findByUserIdAndType(Integer userId, TokenType tokenType);

  void deleteByUserId(Integer userId);
}
