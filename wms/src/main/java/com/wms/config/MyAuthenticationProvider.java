package com.wms.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.wms.entity.User;
import com.wms.repository.UserRepository;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	private UserRepository uRepository;
	private PasswordEncoder pEncoder;
	
	@Autowired
	public void setuRepository(UserRepository uRepository) {
		this.uRepository = uRepository;
	}
	
	@Autowired
	public void setpEncoder(PasswordEncoder pEncoder) {
		this.pEncoder = pEncoder;
	}
	
	
	/**
	 * This is custom Authenticaion provider
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username= authentication.getName();
        String password= authentication.getCredentials().toString();
        
        
        Optional<User> ob= uRepository.findByEmail(username);
        if(ob.isEmpty())
		{
			throw new BadCredentialsException("No User registerd with this details");
		}
		else
		{
          User user= ob.get();
          if(pEncoder.matches(password, user.getPassword()))
          {
        	  List<GrantedAuthority> authorities = new ArrayList<>();
        	  
        	  return new UsernamePasswordAuthenticationToken(username,password, authorities);
          }
          else {
        	  throw new BadCredentialsException("No User registerd with this details");
		}
		}
 		
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		
		
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	

}
