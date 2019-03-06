package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByEmail(String email);
  Optional<User> findById(Integer id);

}
