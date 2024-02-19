package com.wms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.wms.exceptions.UserNotLoggedException;
import com.wms.service.WishlistService;

public class WishlistControllerTest {
 
	@Mock
	private WishlistService wService;
	
	@InjectMocks
	private WishlistController wController;
	
	@Test
	void addItem_Sucess() {
		
		//Mock Authentication 
		Authentication auth= new UsernamePasswordAuthenticationToken("akt00071000@gmail.com", "Ankit12345");
		
		//Mock wishlistservice response
		 when(wService.addItem(anyInt(), anyString())).thenReturn("Item added sucessfully");
		 
		 ResponseEntity<String> responseEntity = wController.addItem(1, auth);
		 
		 assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
		 assertEquals("Item added sucessfully",responseEntity.getBody());
		 
		 verify(wService,times(1)).addItem(1, "akt00071000@gmail.com");
		
	}
	
   @Test
   void addItem_UserNotLogged_Exception() {
	   
	   Authentication auth = new UsernamePasswordAuthenticationToken(null,null);
	   assertThrows(UserNotLoggedException.class, ()-> wController.addItem(1, auth));
	   verify(wService,never()).addItem(anyInt(), anyString());
   }
   
   
   void 

}
