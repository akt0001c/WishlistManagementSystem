package com.wms.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wms.entity.Product;
import com.wms.exceptions.ProductNotFoundException;
import com.wms.repository.ProductRepository;
import com.wms.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository prepo;

	@Autowired
	public void setPrepo(ProductRepository prepo) {
		this.prepo = prepo;
	}

	@Override
	public Product addProduct(Product product) {
		
		log.info("service for adding product started...");
		Product res=null;
		res= prepo.save(product);
		log.info("service response, product added sucessfully");
		return res;
	}

	@Override
	public Product removeProduct(Integer pid) {
		log.info("Service for removing product started...");
		Product res= prepo.findById(pid).orElseThrow(()->new ProductNotFoundException("Product not found or invalid prouct id :"+pid));
		prepo.delete(res);
		
		log.info("Service response , Product removed from the database sucessfully");
		return res;
	}

	@Override
	public List<Product> getAllProduct(String field,String direction,Integer pageno,Integer records) {
		
		log.info("Service getAllProduct started...");
		
		if(field==null)
			 field="addedAt";
		
		if(direction ==null)
			 direction= "desc";
		
		if(pageno==null)
			 pageno=1;
		
		if(records==null)
			records=10;
		
        Sort sort= direction.equalsIgnoreCase("asc")?Sort.by(field).ascending():Sort.by(field).descending();
        
       
		
		Pageable pb= PageRequest.of(pageno-1, records, sort);
		 Page<Product> page= prepo.findAll(pb);
		 
		 List<Product> res= page.getContent();
		 
		 log.info("service response ,Product list found sucessfully");
		return res;
	}

}
