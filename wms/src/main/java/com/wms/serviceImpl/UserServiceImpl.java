package com.wms.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.entity.User;
import com.wms.entity.Wishlist;
import com.wms.exceptions.OperationFaliureException;
import com.wms.exceptions.UserAlreadyExistException;
import com.wms.exceptions.UserNotFoundException;
import com.wms.repository.UserRepository;
import com.wms.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	private UserRepository urepo;
	

	@Autowired
	public void setUrepo(UserRepository urepo) {
		this.urepo = urepo;
	}

	@Override
	public User registerUser(User user) {
		log.info(" user registration service started... ");
		
		Optional<User> ob= urepo.findByEmail(user.getEmail());
		if(ob.isPresent())
			  throw new UserAlreadyExistException("Email is already registered with another user");
		
		User res=null;
		Wishlist ws= new Wishlist();
		 user.setWishlist(ws);
		 res= urepo.save(user);
		 
		 if(res==null)
			   throw new OperationFaliureException("Signup gor fallied please try again");
		
		
		return res;
	}

	
	@Override
	public User getUserDetails(String email) {
		 User res=null;
		 res= urepo.findByEmail(email).orElseThrow(()->new UserNotFoundException("Email invalid or null"));
		 
		return res;
	}
   
}
