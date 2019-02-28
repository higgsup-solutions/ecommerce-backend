package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.UserDTO;
import com.higgsup.xshop.entity.User;

public interface IUserService {

  User getByEmail(String email);

  User register(User user);

  UserDTO getCurrentUserInfo();

}
