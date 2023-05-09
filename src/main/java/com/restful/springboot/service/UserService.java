package com.restful.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restful.springboot.dto.UserDTO;
import com.restful.springboot.exception.RecordNotFoundException;
import com.restful.springboot.model.User;
import com.restful.springboot.repository.UserRepository;

@Service
public class UserService implements UserDetailsService , UserDetailsPasswordService
{
	@Autowired
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	//@Autowired
	//	public UserService(UserRepository repository) {
	//		super();
	//		this.userRepository = repository;
	//	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}

	public User createUser(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		return userRepository.save(user);
	}

	public User getUserById(Long id) {
		Optional<User> optionUser = userRepository.findById(id);

		if(optionUser.isPresent())
		{
			return optionUser.get();

		}else
		{
			throw new RecordNotFoundException("Record not found ");
		}
	}

	public List<UserDTO> getAllUser() {

		List<User> userList = userRepository.findAll();

		if(userList.isEmpty())
		{
			throw new RecordNotFoundException("Record not found");
		}else
		{
			return convertDtoToEntity(userList);
		}	
	}

	public User updateUser(User user) {

		User existUser = userRepository.findById(user.getId()).orElseThrow( 
				()-> new RecordNotFoundException("Record not found"));
		existUser.setUsername(user.getUsername());
		existUser.setPassword(user.getPassword());
		existUser.setRoles(user.getRoles());
		User updateUser = userRepository.save(existUser);	
		return updateUser;
	}

	public User deleteUser(Long id){

		User user = userRepository.findById(id).orElseThrow(()->new RecordNotFoundException("User Not Found!"));
		userRepository.deleteById(id);
		return user;
	}

	@Override
	public UserDetails updatePassword(UserDetails user, String newPassword) {

		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = getUserByUsername(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		detailsChecker.check(user.get());
		return user.get();
	}

	private Optional<User> getUserByUsername(String usernameValue) {

		String username = usernameValue.trim();
		if (StringUtils.isEmpty(username)) {
			return Optional.empty();
		}
		return userRepository.findByUsername(username);
	}


	private static List<UserDTO> convertDtoToEntity(List<User> userList) {
		List<UserDTO> dtoList = new ArrayList<>();

		for(User user2 : userList) 
		{
			UserDTO dto = new UserDTO();

			dto.setId(user2.getId());
			dto.setUsername(user2.getUsername());
			dto.setFirstName(user2.getFirstName());
			dto.setLastName(user2.getLastName());
			dto.setMobileNumber(user2.getMobileNumber());
			dto.setRoles(user2.getRoles());
			dto.setAccountNonExpired(user2.isAccountNonExpired());
			dto.setAccountNonLocked(user2.isAccountNonLocked());
			dto.setCredentialsNonExpired(user2.isCredentialsNonExpired());
			dto.setEnabled(user2.isEnabled());
			dtoList.add(dto);
		}

		return dtoList;
	}
}
