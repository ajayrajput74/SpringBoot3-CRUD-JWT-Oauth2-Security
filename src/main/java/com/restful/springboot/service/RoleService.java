package com.restful.springboot.service;

import com.restful.springboot.model.Role;
import com.restful.springboot.model.User;

public interface RoleService 
{
	
	//method create for get User By id 
	  Role getRoleById(Long id);
	
	 //method create for update user 
	  Role updateRole(Role role);
	  
//	  method create for delete User
//	  void deleteRole(Long id);

}
