package com.restful.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restful.springboot.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

	//marker interface
}
