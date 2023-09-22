package org.formation.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorDto> handleNotFoundException(HttpServletRequest request, Throwable ex) {
		
		ErrorDto errorDto = new ErrorDto(ex.getLocalizedMessage(), "WARN", new Date());
		return new ResponseEntity<ErrorDto>(errorDto,HttpStatus.NOT_FOUND);
	}
	
	
}
