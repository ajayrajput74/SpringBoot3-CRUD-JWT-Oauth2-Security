package com.restful.springboot.securityhandler;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HandlerAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws ServletException {

		Map<String, Object> map = Map.of(
				"code", "ACCESS_DENIED",
				"message", accessDeniedException.getMessage()
				);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		try {
			var mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), map);
		} catch (Exception e) {
			throw new ServletException();
		}
	}

}
