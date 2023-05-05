package com.restful.springboot.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.restful.springboot.model.Role;
import com.restful.springboot.model.User;
import com.restful.springboot.repository.RoleRepository;
import com.restful.springboot.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService 
{
	
	private RoleRepository repository;
	
	public RoleServiceImpl(RoleRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Role getRoleById(Long id) {
		
		Optional<Role> role = repository.findById(id);
		return role.get();
	}

	@Override
	public Role updateRole(Role role) {
	
		Role existrole = repository.findById(role.getId()).get();
		
		existrole.setName(role.getName());
		Role updateRole=repository.save(existrole);
		return updateRole;
	
	}
}
