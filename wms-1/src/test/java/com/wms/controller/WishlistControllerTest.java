package com.wms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.wms.entity.Wishlist;
import com.wms.service.WishlistService;
@ExtendWith(MockitoExtension.class)
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
		 Mockito.when(wService.addItem(anyInt(), anyString())).thenReturn("Item added sucessfully");
		 
		 ResponseEntity<String> responseEntity = wController.addItem(1, auth);
		 
		 assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
		 assertEquals("Item added sucessfully",responseEntity.getBody());
		 
		 verify(wService,times(1)).addItem(1, "akt00071000@gmail.com");
		
	}
	
//   @Test
//   void addItem_UserNotLogged_Exception() {
//	   //Mock Authentication
//	   Authentication auth = new UsernamePasswordAuthenticationToken(null,"Ankit12345");
//	   
//	   //testing for throwing exception
//	   assertThrows(UserNotLoggedException.class, ()-> wController.addItem(1, auth));
//	   verify(wService,never()).addItem(anyInt(), anyString());
//   }
   
   
   @Test
   void  removeItem_Success() {
	   Authentication auth= new UsernamePasswordAuthenticationToken("akt00071000@gmail.com", "Ankit12345");
	   
	   Mockito.when(wService.removeItem(anyInt(), anyString())).thenReturn("Item removed sucessfully");
	   ResponseEntity<String> res = wController.removeItem(1, auth);
	   
	   assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
	   assertEquals("Item removed sucessfully", res.getBody());
	   
	   verify(wService,times(1)).removeItem(1, "akt00071000@gmail.com");
   }
//   
//   @Test
//   void removeItem_UserNotLogged_Exception() {
//	   //Mock Authentication
//	   Authentication auth = new UsernamePasswordAuthenticationToken(null,"Ankit12345");
//	   
//	   //testing for throwing exception
//	   assertThrows(UserNotLoggedException.class, ()-> wController.removeItem(1, auth));
//	   verify(wService,never()).addItem(anyInt(), anyString());
//   }
   
   
//   @Test
//   void getUserWishlist_UserNotLogged_Exception() {
//	   //Mock Authentication
//	   Authentication auth = new UsernamePasswordAuthenticationToken(null,"Ankit12345");
//	   
//	   //testing for throwing exception
//	   assertThrows(UserNotLoggedException.class, ()-> wController.removeItem(1, auth));
//	   verify(wService,never()).addItem(anyInt(), anyString());
//   }
   
   @Test
   void getUserWishlist_Success() { 
	   Authentication auth = new UsernamePasswordAuthenticationToken("akt00071000@gmail.com", "Ankit12345");
	    Wishlist ob = new Wishlist();
	    ob.setWid(1);

	    Mockito.when(wService.getAllWishListItem(anyString())).thenReturn(ob);

	    ResponseEntity<Wishlist> res = wController.getUserWishlist(auth);

	    assertEquals(HttpStatus.ACCEPTED, res.getStatusCode());
	    assertEquals(1, res.getBody().getWid());

	    verify(wService, times(1)).getAllWishListItem("akt00071000@gmail.com");
	   
   } 

}
