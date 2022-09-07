package com.myretail.exception;

import javax.naming.ServiceUnavailableException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.myretail.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(value = ProductNotFoundException.class)

	public ResponseEntity<ErrorResponse> hanldeNotFoundException(ProductNotFoundException exception) {
		log.error(exception.getClass().toString(),exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),exception.getMessage()));
	}
	
	@ExceptionHandler(value = ServiceUnavailableException.class)

	public ResponseEntity<ErrorResponse> hanldeRemoteApiDownException(ServiceUnavailableException exception) {
		log.error(exception.getClass().toString(),exception.getMessage());
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(),exception.getMessage()));
	}	
	@ExceptionHandler(value = Exception.class)

	public ResponseEntity<ErrorResponse> hanldeAllException(Exception exception) {
		log.error(exception.getClass().toString(),exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Something is went wrong, please try again later."));
	}		
}
