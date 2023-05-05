package com.restful.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restful.springboot.model.Role;
import com.restful.springboot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
 
	 //marker interface
}
