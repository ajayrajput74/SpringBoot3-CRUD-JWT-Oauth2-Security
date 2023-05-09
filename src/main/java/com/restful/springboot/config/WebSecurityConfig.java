package com.restful.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.restful.springboot.filter.AuthTokenFilter;
import com.restful.springboot.securityhandler.HandlerAccessDeniedHandler;
import com.restful.springboot.securityhandler.HandlerAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig 
{
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthTokenFilter authTokenFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("http password encoder method");
		return new BCryptPasswordEncoder();
	}

	@Bean // (2)
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

		return authConfig.getAuthenticationManager();
	}

	@Bean // (3)
	public AuthenticationProvider authenticationProvider() { 

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

		http

		.csrf()
		.disable()
		.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
		.authenticationProvider(authenticationProvider())
		// Set unauthorized requests exception handler
		.exceptionHandling()
		.authenticationEntryPoint(new HandlerAuthenticationEntryPoint())
		.accessDeniedHandler(new HandlerAccessDeniedHandler())
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeHttpRequests()
		.requestMatchers("/user/authenticate").permitAll()
		.requestMatchers("/user/save").hasAnyAuthority("User")
		.requestMatchers("user/getAllUserList").hasAnyAuthority("User","Admin")
		.requestMatchers("/user/getUser/*").hasAnyAuthority("User","Admin")
		.requestMatchers("/userRole/updateUserRole").hasAnyAuthority("User","Admin")
		.requestMatchers("/user/deleteUserById/*").hasAnyAuthority("User","Admin");
		return http.build();
	}
}
