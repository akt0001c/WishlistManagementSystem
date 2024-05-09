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
	
	/**
	 * This method is used as a controller to add Product into the database 
	 * url--> /api/add/product
	 * Product body will be passed as request body and it should be valid 
	 *  product:{
	 *            pimage:"string",//mandatory
	 *            pname:"String",//mandatory
	 *            description:"String" //mandatory
	 *          }
	 * @param Product object 
	 * @return ResponseEntity object of type Product object with id
	 */
	@PostMapping("/add/product")
	public ResponseEntity<Product> addProduct(@Valid  @RequestBody Product product){
		log.info("controller for adding product started...");
		Product res= pservice.addProduct(product);
		
		log.info("product controller response ,Product added sucessfully");
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
	
	
	
	/**
	 * This method is used to get the product list and if we do not any extra parm like field ,direction(dir) ,pageno,records then default values will be taken 
	 * for these params and these params are not mandatory
	 * url--> /api/get/products
	 * @param field a string value which will be used in sorting 
	 * @param direction -  asc or desc
	 * @param pageno - which page no of data is required
	 * @param records - Number of records needed in one page
	 * @return ResponseEntity object of type list of Product object
	 */
	@GetMapping("/get/products")
	public ResponseEntity<List<Product>> getProducts(@RequestParam( value= "field",required=false) String field, @RequestParam( value= "dir" ,required=false) String direction , @RequestParam( value="pageno", required=false) Integer pageno, @RequestParam( value="records" , required=false) Integer records){
		
		log.info("controller for getting all products started...");
		List<Product> res= pservice.getAllProduct(field,direction,pageno,records);
		log.info("Product controller response , all product found sucessfully");
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
	
	
	
	/**
	 * This method is used to remove the product from the database using its product id
	 * url--> /api/product/pid(pathvariable)
	 * @param pid -> It is an Integer type variable that is used to pass product id for delete operation
	 * @return ResponseEntiy of type product which has been removed from the database
	 */
	@DeleteMapping("/product/{pid}")
	public ResponseEntity<Product> remove(@PathVariable("pid") Integer pid){
		
		log.info("controller for removing product started....");
		Product res= pservice.removeProduct(pid);
		
		log.info("Product controller response , Prouduct has been removed ");
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
}
