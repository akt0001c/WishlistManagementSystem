package com.wms.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.wms.entity.Wishlist;
import com.wms.repository.ProductRepository;
import com.wms.repository.UserRepository;
import com.wms.repository.WishlistRepository;
import com.wms.service.WishlistService;

public class WishlistServiceImpl implements WishlistService {

	private UserRepository urepo;
	private WishlistRepository wrepo;
	private ProductRepository prepo;
	
	@Autowired
	public void setPrepo(ProductRepository prepo) {
		this.prepo = prepo;
	}
	
	@Autowired
	public void setUrepo(UserRepository urepo) {
		this.urepo = urepo;
	}
	
	@Autowired
	public void setWrepo(WishlistRepository wrepo) {
		this.wrepo = wrepo;
	}

	@Override
	public String addItem(Integer pid, String email) {
		
		return null;
	}

	@Override
	public Wishlist getAllWishListItem(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeItem(Integer pid, String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
