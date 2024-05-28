package com.wms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wms.entity.Product;
import com.wms.entity.User;
import com.wms.entity.UserStatus;
import com.wms.entity.Wishlist;
import com.wms.entity.WishlistDetails;
import com.wms.exceptions.ProductNotFoundException;
import com.wms.exceptions.SomethingwentWrongException;
import com.wms.exceptions.UserNotLoggedException;
import com.wms.repository.ProductRepository;
import com.wms.repository.UserRepository;
import com.wms.repository.WishlistDetailsRepository;
import com.wms.repository.WishlistRepository;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceImplTest {
	
	@Mock
	private UserRepository  urepo;
	
	@Mock
	private ProductRepository prepo;
	
	@Mock
	private WishlistRepository wrepo;
	
	@Mock
	private WishlistDetailsRepository wdrepo;
	
	@InjectMocks
	private WishlistServiceImpl wservice;
	
	
	private User user;
	private Product product;
	private Wishlist wishlist;
	
	
	@BeforeEach
	public void inti() {
		user = new User();
		user.setUserId(1);
		user.setEmail("ankit@gmail.com");
		user.setName("Ankit");
		user.setMobno("1234567892");
		user.setLocation("Delhi");
		user.setPassword("MyPassword");
		user.setStatus(UserStatus.Active);
		
		product = new Product();
		
		product.setPid(11);
		product.setPimage("this/is/testing/url");
		product.setPname("Test Product");
		product.setDescription("This is test description");
		
		
		wishlist = new Wishlist();
		wishlist.setWid(21);
	}

	@Test
	void addItem_Success() {
		Mockito.when(urepo.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(wrepo.findByUser(Mockito.anyInt())).thenReturn(Optional.of(wishlist));
		Mockito.when(prepo.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		Mockito.when(wdrepo.save(Mockito.any())).thenReturn(null);
		Mockito.when(wrepo.save(Mockito.any())).thenReturn(null);
		
		String response= wservice.addItem(11,"ankit@gmail.com");
		Assertions.assertNotNull(response,"Response cannot be null");
		Assertions.assertEquals("Item added in wishlist sucessfully", response,"Response message didn't match");
		
	}
	
	@Test
	@DisplayName("Method should throw User not logged Exception")
	void addItem_WhenInvalidUserDetailsReceivedShouldThrowUserNotLoggedException() {
		Mockito.when(urepo.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		Assertions.assertThrows(UserNotLoggedException.class, ()->{
			  wservice.addItem(1, "ankit@gmail.com");
		});
	}
	
	
	@Test
	@DisplayName("Method should throw something went wrong exception")
	void addItem_WhenInvalidUseridpassedShouldThrowSomethingWentWrongException() {
		Mockito.when(urepo.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(wrepo.findByUser(Mockito.anyInt())).thenReturn(Optional.empty());
		Assertions.assertThrows(SomethingwentWrongException.class, ()->{
			  wservice.addItem(1, "ankit@gmail.com");
		});
	}
	
	@Test
	@DisplayName("Method should throw ProductNotFound exception when invalid product not found")
	void addItem_WhenProductNotFoundThenshouldThrowProductNotFoundException() {
		Mockito.when(urepo.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(wrepo.findByUser(Mockito.anyInt())).thenReturn(Optional.of(wishlist));
		Mockito.when(prepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
	
		Assertions.assertThrows(ProductNotFoundException.class, ()->{
			  wservice.addItem(1, "ankit@gmail.com");
		});
	}
	
	@Test
	@DisplayName("Wishlist item can be received")
	void getAllWishlistItem_WhenMethodcalledShoudReturnWishlist() {
		Mockito.when(urepo.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(wrepo.findByUser(Mockito.anyInt())).thenReturn(Optional.of(wishlist));
		
		Wishlist response= wservice.getAllWishListItem("ankit@gmail.com");
		Assertions.assertNotNull(response,"Wishlist cannot be null");
		Assertions.assertEquals(response.getWid(), 21,"Wishlist id should be same as excepected");
	}
	
	
	@Test
	@DisplayName("Method should throw UserNotLoggedException")
	void getAllWishlistItem_WhenNullEmailPassedShouldthrowUserNotLoggedException() {
		Assertions.assertThrows(UserNotLoggedException.class, ()->{wservice.getAllWishListItem(null);},"Method should throw UserNotLoggedException");
   
	}
	
	@Test
	@DisplayName("Method should throw SomethingwentwrongException")
	void getAllWishlistItem_WhenInvalidUserIdPassedShouldThrowSomeThingWentWrongException() {
		Mockito.when(urepo.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(wrepo.findByUser(Mockito.anyInt())).thenReturn(Optional.empty());
		Assertions.assertThrows(SomethingwentWrongException.class, ()->wservice.getAllWishListItem("ankit@gmailcom"),"Method should throw somethingwentwrong excetion");
	}
	
	
	@Test
	@DisplayName("Item can be removed from the wishlist")
	void removeItemTest_WhenValidProductIdAndEmailPassedItemShouldRemovedFromWishlist() {
		
		WishlistDetails wd= new WishlistDetails();
		wd.setProduct(product);
		
		List<WishlistDetails> list = new ArrayList<>();
		list.add(wd);
		wishlist.setWishlistDetails(list);
		Mockito.when(urepo.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(wrepo.findByUser(Mockito.anyInt())).thenReturn(Optional.of(wishlist));
		Mockito.when(wrepo.save(Mockito.any())).thenReturn(wishlist);
	    Mockito.doNothing().when(wdrepo).delete(Mockito.any());
	    
	    String response= wservice.removeItem(product.getPid(), "ankit@gmail.com");
	    Assertions.assertNotNull(response,"Response cannot be null");
	    Assertions.assertEquals("Item has been removed from the wishlist", response,"Response should be same as excepted");
		
		
	}
	
	
	
	
	
}
