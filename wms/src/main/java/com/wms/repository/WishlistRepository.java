package com.wms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wms.entity.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Integer>{
	Optional<Wishlist> findByUser(Integer userId);
}
