package com.wms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wms.entity.Product;
import com.wms.service.ProductService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class ProductController {
	
	@Autowired
	public ProductService pservice;

	@PostMapping("/add/product")
	public ResponseEntity<Product> add(@Valid  @RequestBody Product product){
		log.info("controller for adding product started...");
		Product res= pservice.addProduct(product);
		
		log.info("product controller response ,Product added sucessfully");
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
	
	
	
	@GetMapping("/get/products")
	public ResponseEntity<List<Product>> getProducts(@RequestParam( value= "field",required=false) String field, @RequestParam( value= "dir" ,required=false) String direction , @RequestParam( value="pageno", required=false) Integer pageno, @RequestParam( value="records" , required=false) Integer records){
		
		log.info("controller for getting all products started...");
		List<Product> res= pservice.getAllProduct(field,direction,pageno,records);
		log.info("Product controller response , all product found sucessfully");
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("/product/{pid}")
	public ResponseEntity<Product> remove(@PathVariable("pid") Integer pid){
		
		log.info("controller for removing product started....");
		Product res= pservice.removeProduct(pid);
		
		log.info("Product controller response , Prouduct has been removed ");
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
}
