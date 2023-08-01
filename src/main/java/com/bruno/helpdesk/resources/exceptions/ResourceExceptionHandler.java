package com.bruno.helpdesk.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bruno.helpdesk.services.exceptions.ObjectNotFoundExeception;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundExeception.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundExeception ex,
			HttpServletRequest request){
	StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
			"Object not found!", ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
