package com.restful.springboot.model;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User implements UserDetails
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@NotNull(message = "First name cannot be null")
	@NotBlank(message = "First name cannot blank")
	@Column(name = "first_name" ,nullable = false)
	private String firstName;

	@NotNull(message = "Last name cannot be null")
	@NotBlank(message = "Last name cannot blank")
	@Column(name = "last_name" ,nullable = false)
	private String lastName;

	@NotNull(message = "Mobile number cannot be null")
	@Size(min = 10,max = 10,message = "Mobile number have at least 10 digit")
	@Column(name = "mobile_number" , nullable = false)
	private String mobileNumber;

	@NotNull(message = "Username cannot be null")
	@NotBlank(message = "Username cannot blank")
	@Column(name = "username" , unique = true , nullable = false)
	private String username;

    //@Size(min = 8 , message = "Password should have atleast 8 digit")
	//@JsonIgnore
	@NotNull(message = "Password cannot be null")
	@NotBlank(message = "Password cannot blank")
	@Column(name = "password")
	private String password;

	@Column(name = "enabled")
	private Boolean enabled;

	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;

	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;

	@ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinTable(name = "user_role" ,joinColumns = @JoinColumn(name = "user_id" , referencedColumnName = "user_id"),
	inverseJoinColumns = @JoinColumn(name ="role_id" , referencedColumnName = "role_id"))
	private Set<Role> roles; 


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		roles.stream().forEach(r -> System.out.println(r.getName()));
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
	}

	@Override
	public boolean isAccountNonExpired() {

		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {

		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {

		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}




}
