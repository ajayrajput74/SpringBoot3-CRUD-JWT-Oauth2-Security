package com.restful.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restful.springboot.model.Role;
import com.restful.springboot.service.RoleService;

@RestController
@RequestMapping(value = "/userRole")
public class RoleController 
{
	RoleService service ;

	@Autowired
	public RoleController(RoleService service) {
		super();
		this.service = service;
	}

	@GetMapping(value = {"/getRole"})
	public ResponseEntity<Role> getRoleById(@RequestParam(value = "id") Long id)
	{	
		return new ResponseEntity<>(service.getRoleById(id), HttpStatus.OK);
	}
	
	@PutMapping({"/updateRole"})
	public ResponseEntity<Role> updateRole(@RequestParam(value = "id") Long id,@RequestBody Role role){
		role.setId(id);
		Role updatedrole = service.updateRole(role);
		return new ResponseEntity<>(updatedrole, HttpStatus.OK);
	}
}
