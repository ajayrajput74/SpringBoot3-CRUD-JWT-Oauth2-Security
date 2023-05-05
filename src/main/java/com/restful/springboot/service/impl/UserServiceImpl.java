package com.restful.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.restful.springboot.model.User;
import com.restful.springboot.repository.UserRepository;
import com.restful.springboot.service.UserService;

@Service
public class UserServiceImpl implements UserService 
{
	private UserRepository repository;
	
	//working on constructor dependency injection
	public UserServiceImpl(UserRepository repository) {
		super();
		this.repository = repository;
	}
     
	// method that save user into the database
	@Override
	public User createUser(User user) {
		return repository.save(user);
	}

	// making method that get user by id
	@Override
	public User getUserById(Long id) {
		Optional<User> optionUser = repository.findById(id);
		return optionUser.get();
	}

	//making method that getall User
	@Override
	public List<User> getAllUser() {
		
		return repository.findAll();
	}

	//making method that update user by id
	@Override
	public User updateUser(User user) {
		User existUser = repository.findById(user.getId()).get();
		existUser.setUsername(user.getUsername());
		existUser.setPassword(user.getPassword());
		existUser.setRoles(user.getRoles());
		User updateUser = repository.save(existUser);	
		return updateUser;
	}

	// making method that can deleteUser into the database by using id
	@Override
	public void deleteUser(Long id) {
		
		repository.deleteById(id);
	}
}
