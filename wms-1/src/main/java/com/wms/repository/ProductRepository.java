package com.wms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.wms.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>,PagingAndSortingRepository<Product,Integer>{

}
