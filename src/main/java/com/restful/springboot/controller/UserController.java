package com.restful.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restful.springboot.model.User;
import com.restful.springboot.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController 
{
	private UserService service;

	@Autowired
	public UserController(UserService service) {
		super();
		this.service = service;
	}

	@PostMapping(value = {"/save"})
	public ResponseEntity<User> createUser(@RequestBody User user)
	{
		User saveUser = service.createUser(user);	
		return new ResponseEntity<>(saveUser ,HttpStatus.CREATED);
	}

	@GetMapping(value = {"/getUser"})
	public ResponseEntity<User> getUserById(@RequestParam(value = "id") Long id)
	{	
		return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
	}


	@GetMapping({"/getAllUserList"})
	public ResponseEntity<List<User>> getAllUser()
	{
		return new ResponseEntity<>(service.getAllUser(), HttpStatus.OK);
	}

	@PutMapping({"/updateProfile"})
	public ResponseEntity<User> updateUser(@RequestParam(value = "id") Long id,@RequestBody User user){
		user.setId(id);
		User updatedUser = service.updateUser(user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@DeleteMapping({"/deleteUserById"})
	public ResponseEntity<User> deleteUser(@RequestParam(value = "id") Long id)
	{
		service.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);	
	}
}
