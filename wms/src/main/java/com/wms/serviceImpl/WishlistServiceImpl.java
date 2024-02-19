package com.wms.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wms.entity.Product;
import com.wms.entity.User;
import com.wms.entity.Wishlist;
import com.wms.entity.WishlistDetails;
import com.wms.exceptions.ProductNotFoundException;
import com.wms.exceptions.SomethingwentWrongException;
import com.wms.exceptions.UserNotLoggedException;
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
		User user= urepo.findByEmail(email).orElseThrow(()->new UserNotLoggedException("User shouble be logged in "));
		Wishlist wishlist= wrepo.findByUser(user.getUserId()).orElseThrow(()->new SomethingwentWrongException("wishlist not found for the logged user"));
		Product product = prepo.findById(pid).orElseThrow(()->new ProductNotFoundException("Product not found for id :"+pid));
		wishlist.setUser(user);
		WishlistDetails wd= new WishlistDetails();
		wd.setProduct(product);
		wd.setWishlist(wishlist);
		product.getWishlistDetails().add(wd);
		wishlist.getWishlistDetails().add(wd);
		
		
	    wrepo.save(wishlist);
		
		return "Item added in wishlist sucessfully";
	}

	@Override
	public Wishlist getAllWishListItem(String email) {
		User user= urepo.findByEmail(email).orElseThrow(()->new UserNotLoggedException("User shouble be logged in "));
		Wishlist wishlist= wrepo.findByUser(user.getUserId()).orElseThrow(()->new SomethingwentWrongException("wishlist not found for the logged user"));
		return wishlist;
	}

	@Override
	public String removeItem(Integer pid, String email) {
		User user= urepo.findByEmail(email).orElseThrow(()->new UserNotLoggedException("User shouble be logged in "));
		Wishlist wishlist= wrepo.findByUser(user.getUserId()).orElseThrow(()->new SomethingwentWrongException("wishlist not found for the logged user"));
		List<WishlistDetails> wlist= wishlist.getWishlistDetails();
		for(WishlistDetails ob :wlist)
		{
			if(ob.getProduct().getPid()==pid)
			{
				wlist.remove(ob);
				break;
			}
		}
		
		wishlist.setWishlistDetails(wlist);
		
		wrepo.save(wishlist);
		return "Item has been removed from the wishlist";
	}

}
