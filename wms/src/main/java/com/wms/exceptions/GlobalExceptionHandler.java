package com.wms.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(OperationFaliureException.class)
	public ResponseEntity<MyErrorDetails>  operationFaliureExceptionHandler(OperationFaliureException exp,WebRequest req){
		MyErrorDetails err= new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(exp.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<MyErrorDetails>  userNotFoundExceptionHandler(UserNotFoundException exp,WebRequest req){
		MyErrorDetails err= new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(exp.getLocalizedMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	

	@ExceptionHandler(SomethingwentWrongException.class)
	public ResponseEntity<MyErrorDetails>  somethingwentwrongExceptionHandler(SomethingwentWrongException exp,WebRequest req){
		MyErrorDetails err= new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(exp.getLocalizedMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<MyErrorDetails>  productNotFoundExceptionHandler(ProductNotFoundException exp,WebRequest req){
		MyErrorDetails err= new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(exp.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<>(err,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotLoggedException.class)
	public ResponseEntity<MyErrorDetails>  userNotLoggedInExceptionHandler(UserNotLoggedException exp,WebRequest req){
		MyErrorDetails err= new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(exp.getLocalizedMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<>(err,HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails>  noValidArgumentExceptionHandler(MethodArgumentNotValidException exp){
		MyErrorDetails err= new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(exp.getLocalizedMessage());
		err.setDetails(exp.getBindingResult().getFieldError().getDefaultMessage());
		
		return new ResponseEntity<>(err,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<MyErrorDetails>  userAlreadyExistExceptionHandler(UserAlreadyExistException exp,WebRequest req){
		MyErrorDetails err= new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(exp.getLocalizedMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails>  myExceptionHandler(Exception exp,WebRequest req){
		MyErrorDetails err= new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(exp.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorDetails>  noHandlerFoundExceptionHandler(Exception exp,WebRequest req){
		MyErrorDetails err= new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(exp.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}

}
