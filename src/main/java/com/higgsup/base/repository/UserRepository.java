package com.higgsup.base.repository;

import com.higgsup.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u left join fetch u.roles r where u.username=:username")
  User findByUsername(@Param("username") String username);

  @Query(value = "SELECT * FROM APP_USER WHERE UPPER(username) like 'N%' ", nativeQuery = true)
  List<User> findAllUsernameStartWithN();

  List<User> findUserById(Long id);
}
