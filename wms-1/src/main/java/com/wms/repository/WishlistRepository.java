package com.wms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wms.entity.Wishlist;



@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Integer>{
	
	@Query("select w from Wishlist w where w.user.userId=?1 ")
	Optional<Wishlist>  findByUser(Integer user_id);
}

