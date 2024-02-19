package com.wms.service;

import com.wms.entity.User;

public interface UserService {
  public User registerUser(User user);
  
  public User getUserDetails(String email);
}
