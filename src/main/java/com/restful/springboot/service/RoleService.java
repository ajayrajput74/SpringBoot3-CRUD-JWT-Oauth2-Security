package com.restful.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restful.springboot.exception.RecordNotFoundException;
import com.restful.springboot.model.Role;
import com.restful.springboot.repository.RoleRepository;

@Service
public class RoleService 
{

	@Autowired
	private RoleRepository repository;

	public RoleService(RoleRepository repository) {
		super();
		this.repository = repository;
	}

	public Role getRoleById(Long id) {

		Optional<Role> role = repository.findById(id);

		if(role.isPresent()) 
		{
			return role.get();
		}else
		{
			throw new RecordNotFoundException("record not found ");
		}
	}

	public Role updateRole(Role role) {

		Role existrole = repository.findById(role.getId()).orElseThrow(
				()-> new RecordNotFoundException("Record not found "));

		existrole.setName(role.getName());
		Role updateRole=repository.save(existrole);
		return updateRole;
	}

	public List<Role>getAllRole(){

		List<Role> role= repository.findAll();

		if(role.isEmpty())
		{
			throw new RecordNotFoundException("Record not Found");
		}else
		{
			return role;
		}
	}

	public Role getRollByName(String name) {

		return repository.findByName(name);
	}
}
