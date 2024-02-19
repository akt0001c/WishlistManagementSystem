package com.wms.service;

import com.wms.entity.Wishlist;

public interface WishlistService {
	
 public String addItem(Integer pid,String email);
 public Wishlist getAllWishListItem(String email);
 public String removeItem(Integer pid,String email);
}
