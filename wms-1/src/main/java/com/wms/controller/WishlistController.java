package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wms.entity.Wishlist;
import com.wms.exceptions.UserNotLoggedException;
import com.wms.service.WishlistService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class WishlistController {

@Autowired
public WishlistService wservice;


@PostMapping("/wishlists")
public ResponseEntity<String> addItem(@RequestBody Integer pid ,Authentication auth){
	
	log.info("Wishlist conroller for add item in wishlist started...");
	if(auth.getName()==null)
		   throw new UserNotLoggedException("User should be logged in ");
	String res= null;
	res= wservice.addItem(pid,auth.getName());
	log.info("Wishlist controller response , item added in wishlist");
	return new ResponseEntity<>(res,HttpStatus.CREATED);
}


@DeleteMapping("/wishlists/{id}")
public ResponseEntity<String> removeItem(@PathVariable("id") Integer pid ,Authentication auth){
	log.info("wishlist controller for removing the item started...");
	if(auth.getName()==null)
		   throw new UserNotLoggedException("User should be logged in ");
	
	String res=null;
	res= wservice.removeItem(pid,auth.getName());
	log.info("wishlist controller response , item removed form wishlist");
	return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
}

@GetMapping("/wishlists")
public ResponseEntity<Wishlist> getUserWishlist(Authentication auth){
	
	log.info("Wishlist controller to get complete a wishlist for logged user started...");
	if(auth.getName()==null)
		   throw new UserNotLoggedException("User should be logged in ");
	
	Wishlist res= null;
	res= wservice.getAllWishListItem(auth.getName());
	log.info("Wishlist controller reponse ,user wishlist found sucessfully");
	return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
}

}
