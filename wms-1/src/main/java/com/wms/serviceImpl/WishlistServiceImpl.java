package com.wms.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wms.entity.Product;
import com.wms.entity.User;
import com.wms.entity.Wishlist;
import com.wms.entity.WishlistDetails;
import com.wms.entity.WishlistProductStatus;
import com.wms.exceptions.ProductNotFoundException;
import com.wms.exceptions.SomethingwentWrongException;
import com.wms.exceptions.UserNotLoggedException;
import com.wms.repository.ProductRepository;
import com.wms.repository.UserRepository;
import com.wms.repository.WishlistDetailsRepository;
import com.wms.repository.WishlistRepository;
import com.wms.service.WishlistService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service 
public class WishlistServiceImpl implements WishlistService {

	private UserRepository urepo;
	private WishlistRepository wrepo;
	private ProductRepository prepo;
	private WishlistDetailsRepository wdrepo;
	
	@Autowired
	public void setWdrepo(WishlistDetailsRepository wdrepo) {
		this.wdrepo = wdrepo;
	}

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

	
	/**
	 * This method is used to add product item into the logged user wishlist 
	 * Here we need to pass valid product id which is already added into the database and email will be take form authentication 
	 * @author Ankit Choubey
	 * @param Integer product id(pid), String email (which will be obtain from authentcation object internally)
	 * @return String with message
	 * @exception UserNOtLoggedException, SomethingwentWrongException,ProductNotFoundException
	 */
	@Override
	public String addItem(Integer pid, String email) {
		User user= urepo.findByEmail(email).orElseThrow(()->new UserNotLoggedException("User shouble be logged in "));
		Wishlist wishlist= wrepo.findByUser(user.getUserId()).orElseThrow(()->new SomethingwentWrongException("wishlist not found for the logged user"));
		Product product = prepo.findById(pid).orElseThrow(()->new ProductNotFoundException("Product not found for id :"+pid));
		wishlist.setUser(user);
		
		WishlistDetails wd= new WishlistDetails();
		wd.setQuantity(1);
		wd.setAddedAt(LocalDateTime.now());
		wd.setProduct(product);
		
		wd.setWishlist(wishlist);
		product.getWishlistDetails().add(wd);
		wishlist.getWishlistDetails().add(wd);
		
		
		wdrepo.save(wd);
	    wrepo.save(wishlist);
		
		return "Item added in wishlist sucessfully";
	}

	/**
	 * It is used to get complete wishlist object for a logged user
	 * @author Ankit choubey
	 * @param String email(From Authentication object)
	 * @return Wishlist object
	 * @exception UserNotLoggedException,SomethingwentWrongException
	 */
	@Override
	public Wishlist getAllWishListItem(String email) {
		log.info("Service method to get complete wishlist started...");
		User user= urepo.findByEmail(email).orElseThrow(()->new UserNotLoggedException("User shouble be logged in "));
		Wishlist wishlist= wrepo.findByUser(user.getUserId()).orElseThrow(()->new SomethingwentWrongException("wishlist not found for the logged user"));
		log.info("Service method response wishlist found successfully.");
		return wishlist;
	}

	
	/**
	 * It is used to remove an item from wishlist using product id 
	 * @author Ankit Choubey
	 * @param Integer productid(pid),String email
	 * @return String message 
	 * @exception UserNotLoggedException,SomethingwentWrongException
	 */
	@Override
	public String removeItem(Integer pid, String email) {
		User user= urepo.findByEmail(email).orElseThrow(()->new UserNotLoggedException("User shouble be logged in "));
		Wishlist wishlist= wrepo.findByUser(user.getUserId()).orElseThrow(()->new SomethingwentWrongException("wishlist not found for the logged user"));
		List<WishlistDetails> wlist= wishlist.getWishlistDetails();
		
		boolean res= false; WishlistDetails obDetails=null;
		for(WishlistDetails wd:wlist)
		{ 
			if(wd.getProduct().getPid().equals(pid))
			{
				obDetails=wd;
				break;
			}
		}
		
		if(obDetails!=null)
		{
			wlist.remove(obDetails);
			wishlist.setWishlistDetails(wlist);
			wrepo.save(wishlist);
			wdrepo.delete(obDetails);
			res=true;
		}
		
		
		
		
		
		
		if(res==true)
		return "Item has been removed from the wishlist";
		else {
			return "Item cannot be removed";
		}
	}

}
