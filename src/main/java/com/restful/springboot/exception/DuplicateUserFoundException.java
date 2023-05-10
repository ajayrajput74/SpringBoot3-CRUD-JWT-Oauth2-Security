package com.restful.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DuplicateUserFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DuplicateUserFoundException(String message)
	{
		super(message);
	}
	

}
