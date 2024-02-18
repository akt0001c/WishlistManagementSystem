package com.wms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.wms.entity.Product;
import com.wms.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	public ProductService pservice;

	public ResponseEntity<Product> add(Product product){
		Product res= pservice.addProduct(product);
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
	
	
	public ResponseEntity<List<Product>> getProducts(){
		List<Product> res= pservice.getAllProduct();
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
	
	public ResponseEntity<Product> remove(Integer pid){
		Product res= pservice.removeProduct(pid);
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
}
