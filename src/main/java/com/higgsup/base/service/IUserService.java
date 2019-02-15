package com.higgsup.base.service;

import com.higgsup.base.dto.UserDTO;
import com.higgsup.base.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
  User getByUsername(String username);

  List<User> getUser();

  void createUser(UserDTO userDTO);

  void createUserDemoTransaction(UserDTO userDTO);
}
