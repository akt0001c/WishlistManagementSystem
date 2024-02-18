package com.wms.exceptions;

public class UserAlreadyExistException extends RuntimeException {

  public	UserAlreadyExistException(){}
  
  public UserAlreadyExistException(String msg) {
	  super(msg);
  }
	
	
}
