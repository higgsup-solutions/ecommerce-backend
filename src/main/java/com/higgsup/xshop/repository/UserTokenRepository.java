package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
}
