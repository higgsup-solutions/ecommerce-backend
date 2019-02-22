package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByEmail(String email);

}
