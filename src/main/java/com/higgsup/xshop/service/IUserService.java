package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.UserDTO;
import com.higgsup.xshop.entity.User;

import java.util.List;

public interface IUserService {
  User getByUsername(String username);

  List<User> getUser();

  void createUser(UserDTO userDTO);

  void createUserDemoTransaction(UserDTO userDTO);
}
