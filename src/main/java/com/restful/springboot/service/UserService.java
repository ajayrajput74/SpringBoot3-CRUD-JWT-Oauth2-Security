package com.restful.springboot.service;

import java.util.List;

import com.restful.springboot.model.User;

public interface UserService 
{
  //method create for save User	
  User createUser(User user);
  
  //method create for get User By id 
  User getUserById(Long id);
  
  //method create for get User List
  List<User> getAllUser();
  
  //method create for update user 
  User updateUser(User user);
  
  // method create for delete User
  void deleteUser(Long id);

}
