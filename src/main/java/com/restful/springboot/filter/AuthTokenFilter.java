package com.restful.springboot.filter;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.restful.springboot.config.TokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter
{
	private TokenProvider tokenProvider;
	
	private  UserDetailsService userService;
	
	@Autowired
	public void setTokenProvider(TokenProvider tokenProvider)
	{
		this.tokenProvider = tokenProvider;
	}
	
	@Autowired
	public void setUserDetailsService(UserDetailsService userService)
	{
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
		System.out.println("token " +request.getHeader("Authorization"));

		Optional<String> jwt = resolveHeaderToken(request);
		//System.out.println("token expired  "+tokenProvider.validateToken(jwt.get()));
		if (jwt.isPresent() && tokenProvider.validateToken(jwt.get())) {
			System.out.println("aftar validate token");
			String username = tokenProvider.extractUsername(jwt.get());
			System.out.println("aftar validate token username  " +username);
			if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userService.loadUserByUsername(username);
				System.out.println("userdetail "+userDetails);
				var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}

	private Optional<String> resolveHeaderToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		return StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ") ? Optional.of(bearerToken.substring(7)) : Optional.empty();
	}

}
