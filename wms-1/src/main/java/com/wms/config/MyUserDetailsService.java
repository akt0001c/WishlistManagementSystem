package com.wms.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wms.entity.MyUserDetails;
import com.wms.entity.User;
import com.wms.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> ob = userRepository.findByEmail(username);
		if(ob.isPresent())
 		{
            	User user= ob.get();
            	
            	return new MyUserDetails(user);
		}
		else {
			throw new BadCredentialsException("NO user found or incorrect email :"+username);
		}
		
		
	}

	

}
