package com.restful.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restful.springboot.config.TokenProvider;
import com.restful.springboot.dto.LoginDTO;
import com.restful.springboot.dto.SuccessResponse;
import com.restful.springboot.dto.UserDTO;
import com.restful.springboot.exception.RecordNotFoundException;
import com.restful.springboot.model.User;
import com.restful.springboot.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController 
{
	private UserService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private  TokenProvider tokenProvider;

	@Autowired
	public UserController(UserService service) {
		super();
		this.service = service;
	}

	@PostMapping(value = {"/save"})
	public ResponseEntity<User> createUser(@Valid @RequestBody User user)
	{
		User saveUser = service.createUser(user);	
		return new ResponseEntity<>(saveUser ,HttpStatus.CREATED);
	}

	@GetMapping(value = {"/getUser/{id}"})
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) throws RecordNotFoundException
	{	
		return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
	}

	@GetMapping({"/getAllUserList"})
	public ResponseEntity<List<UserDTO>> getAllUser()
	{
		return new ResponseEntity<>(service.getAllUser(), HttpStatus.OK);
	}


	@PutMapping({"/updateProfile/{id}"})
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id,@RequestBody User user) throws RecordNotFoundException
	{
		user.setId(id);
		User updatedUser = service.updateUser(user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@DeleteMapping({"/deleteUserById/{id}"})
	public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long id)
	{
		service.deleteUser(id);

		return new ResponseEntity<>(HttpStatus.OK);	
	}

	@PostMapping("/authenticate")
	public ResponseEntity<SuccessResponse> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {

		var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);

		return ResponseEntity.ok(new SuccessResponse(jwt, "Login Successfully"));
	}
}
