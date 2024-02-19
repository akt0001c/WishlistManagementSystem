package com.wms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wms.entity.Wishlist;
import java.util.List;
import com.wms.entity.User;



@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Integer>{
	
	@Query("select w from Wishlist w where w.user=?1 ")
	Optional<Wishlist>  findByUser(Integer userId);
}
