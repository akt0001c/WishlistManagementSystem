package com.wms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wms.entity.WishlistDetails;

@Repository
public interface WishlistDetailsRepository  extends JpaRepository<WishlistDetails, Integer>{

}
