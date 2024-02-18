package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.wms.entity.Wishlist;
import com.wms.service.WishlistService;

@RestController
public class WishlistController {

@Autowired
public WishlistService wservice;
	
public ResponseEntity<String> addItem(Integer pid){
	String res= null;
	res= wservice.addItem(pid);
	return new ResponseEntity<>(res,HttpStatus.CREATED);
}

public ResponseEntity<String> removeItem(Integer pid){
	String res=null;
	res= wservice.removeItem(pid);
	return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
}

public ResponseEntity<Wishlist> getUserWishlist(){
	Wishlist res= null;
	res= wservice.getAllWishListItem();
	return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
}

}
