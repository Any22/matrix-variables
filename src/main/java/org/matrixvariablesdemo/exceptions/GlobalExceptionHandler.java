package org.matrixvariablesdemo.exceptions;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import jakarta.validation.ConstraintViolationException;
/************************************************************************************************************************************
 * a real world application can handle the exceptions and validation failures 
 * @author saba akhtar
 * Generalized handler 
 * Mentioning the type in @ExceptionHandler is mandatory when there are multiple types of exceptions to be handled by a single
 * single handler. 
 ***********************************************************************************************************************************/

@RestControllerAdvice
public class GlobalExceptionHandler {
	@Autowired 
	private Environment environment;
	
	@ExceptionHandler (Exception.class)
	public ResponseEntity<ErrorMessage> generalExceptionHandler(Exception ex){
		
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage("check URL");
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	@ExceptionHandler (CustomerNotFoundException.class)
	public ResponseEntity<ErrorMessage> customerNotFoundExceptionHandler(CustomerNotFoundException ex){
		
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler (NoDataFoundException.class)
	public ResponseEntity<ErrorMessage> noDataFoundExceptionHandler(NoDataFoundException ex){
		
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		
	}
	//Handler for validation failure w.r.t DTOs or validation failures request body 
	@ExceptionHandler (MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> methodArgumentExcpetionHandler (MethodArgumentNotValidException ex){
		
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining(", ")));
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
			
	}
	
	//Handler for validation failure w.r.t URI parameters 
	@ExceptionHandler (ConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> constraintViolationExcpetionHandler ( ConstraintViolationException ex){
		
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
//		error.setMessage(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
//				.collect(Collectors.joining(", ")));
		error.setMessage("check the parameters and data object");
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		
			
	}
}
