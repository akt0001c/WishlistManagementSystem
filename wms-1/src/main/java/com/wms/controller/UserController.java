package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wms.entity.User;
import com.wms.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;




@RestController
@Slf4j
@RequestMapping("/api")
public class UserController {
	
private PasswordEncoder pencoder;

private UserService uservice;

    @Autowired
	public void setUservice(UserService uservice) {
	this.uservice = uservice;
}




	@Autowired
    public void setPencoder(PasswordEncoder pencoder) {
	this.pencoder = pencoder;
}


  
  @PostMapping("/signUp")
  public ResponseEntity<User> signUp(@Valid @RequestBody User user){
	  
	   log.info("User signup started...");
	    user.setPassword(pencoder.encode(user.getPassword()));
	   
	    User res= uservice.registerUser(user);
	    
	    log.info("User signup completed");
	    
	    
	    return new ResponseEntity<>(res,HttpStatus.CREATED);
  }
 
  @GetMapping("/logIn")
  public ResponseEntity<String> logIn(Authentication auth ){
	   log.info("User login started...");
	   String res=null;
	   User user= uservice.getUserDetails(auth.getName());
	   res= user.getName()+" "+"Logged in successfully";
	   
	   log.info(user.getName()+"login sucessfull");
	   return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
  }
}
