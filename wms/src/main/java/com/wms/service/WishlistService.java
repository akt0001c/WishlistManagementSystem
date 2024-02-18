package com.wms.service;

import com.wms.entity.Wishlist;

public interface WishlistService {
	
 public String addItem(Integer pid);
 public Wishlist getAllWishListItem();
 public String removeItem(Integer pid);
}
