package com.wms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.wms.entity.Product;
import com.wms.exceptions.OperationFaliureException;
import com.wms.exceptions.ProductNotFoundException;
import com.wms.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
	
	
	@Mock
	private ProductRepository prepo;
	
	@InjectMocks
	private ProductServiceImpl pservice;
	
	
	private Product reqProduct;
	private Product resProduct;
	
	
	@BeforeEach
	public void init() {
		reqProduct = new Product();
		reqProduct.setPid(1);
		reqProduct.setPname("Book");
		reqProduct.setPimage("test/url");
		reqProduct.setDescription("This is test description");
		
		resProduct = new Product();
		resProduct.setPid(1);
		resProduct.setPname("Book");
		resProduct.setPimage("test/url");
		resProduct.setDescription("This is test description");
		
	}
	
	
	@Test
	@DisplayName("Product can be add")
	public void addProductTest_ValidProductShouldBeAdd() {
		Mockito.when(prepo.save(Mockito.any(Product.class))).thenReturn(resProduct);
		Product res= pservice.addProduct(reqProduct);
		Assertions.assertNotNull(res,"Response cannot be null");
		Assertions.assertEquals(1, res.getPid(),"Product id should be correct");
		
	}
	
	@Test
	@DisplayName("When null or invalid product object passed method should throw OperationFaliureException")
	public void addProductTest_shouldThrowOperationFaliureException() {
	
		Assertions.assertThrows(OperationFaliureException.class, ()->pservice.addProduct(null));
	}
	
	
	@Test
	@DisplayName("When null product id passed , Method should throw OperationFaliureException")
	public void removeProductTest_WhenNullIdPassedShouldThrowOperationFaliureException() {
		Assertions.assertThrows(OperationFaliureException.class,()->pservice.removeProduct(null));
	}
	
	
	@Test
	@DisplayName("When invalid id passed ,Method should throw ProductNotFoundException")
	public void removeProductTest_WhenInvalidIdPassedShouldThrowProductNotFoundException() {
		 
		Mockito.when(prepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Assertions.assertThrows(ProductNotFoundException.class,()->pservice.removeProduct(2),"Method should throw ProductNotFoundException");
	}
	
	@Test
	@DisplayName("Product can be romoved")
	public void removeProductTest_ProductCanBeRemoved() {
		Mockito.when(prepo.findById(Mockito.anyInt())).thenReturn(Optional.of(resProduct));
		Mockito.doNothing().when(prepo).delete(Mockito.any());
		Product res= pservice.removeProduct(1);
		Assertions.assertNotNull(res,"Response cannot be null");
		Assertions.assertEquals(1, res.getPid(),"Reposne Product id should match");
	}
	
	@Test
	@DisplayName("Product list can be found with default arguments")
	public void getAllProductTest_ReturnsProductList() {
		List<Product> list= new ArrayList<>();
		list.add(resProduct);
		Page<Product> page= new PageImpl<>(list);
		
		
		Mockito.when(prepo.findAll(Mockito.any(Pageable.class))).thenReturn(page);
		List<Product> response= pservice.getAllProduct(null, null, null, null);
		
		
	}
	
	
	

}
