package com.wms.service;

import java.util.List;

import com.wms.entity.Product;

public interface ProductService {
 public Product addProduct(Product product);
 public Product removeProduct(Integer pid);
 public List<Product> getAllProduct(String field,String direction,Integer pageno,Integer records);

}
