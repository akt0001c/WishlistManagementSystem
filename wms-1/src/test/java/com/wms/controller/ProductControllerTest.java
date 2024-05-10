package com.wms.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wms.entity.Product;
import com.wms.service.ProductService;

@WebMvcTest(controllers=ProductController.class)
@AutoConfigureMockMvc(addFilters=false)
public class ProductControllerTest {
	
   @Autowired
   private MockMvc mockMvc;
   
   @MockBean
   private ProductService pservices;
	
   private Product requestProduct;
   
   
   private Product responseProduct;
   
   
   @BeforeEach
   public void intit() {
	  requestProduct = new Product();
	  requestProduct.setPimage("api/test/image");
	  requestProduct.setPname("product name");
	  requestProduct.setDescription("This is test description");
	  
	  responseProduct = new Product();
	  responseProduct.setPid(1);
	  responseProduct.setPimage("api/test/image");
	  responseProduct.setPname("Product name ");
	  responseProduct.setDescription("This is test description");
   }
   
    @Test
    @DisplayName("Product can be added")
	public void addProduct_Success() throws Exception {
		
		Mockito.when(pservices.addProduct(Mockito.any(Product.class))).thenReturn(responseProduct);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/add/product").content(new ObjectMapper().writeValueAsString(requestProduct)).contentType(MediaType.APPLICATION_JSON_VALUE);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
		String responseBody= mvcResult.getResponse().getContentAsString();
		Product response= new ObjectMapper().readValue(responseBody, Product.class);
		
		Assertions.assertNotNull(response.getPid(),"Product should have one valid id");
		Mockito.verify(pservices,Mockito.times(1)).addProduct(Mockito.any(Product.class));
		
	}
    
    @Test
    @DisplayName("MethodArgumentNotValid exception for invalid url can be thrown")
    public void testAddProduct_whenInvalidProductUrlPassedShouldthrowMethodArgumentNotValidException() throws Exception {
    	Mockito.when(pservices.addProduct(Mockito.any(Product.class))).thenReturn(responseProduct);
    	requestProduct.setPimage("");
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/add/product").content(new ObjectMapper().writeValueAsString(requestProduct)).contentType(MediaType.APPLICATION_JSON_VALUE);
    	 mockMvc.perform(requestBuilder).andExpect(result->Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)).andReturn();
    }
    
    @Test
    @DisplayName("MethodArgumentNotValid exception for invalid product name can be thrown")
    public void testAddProduct_whenInvalidProductNamePassedShouldthrowMethodArgumentNotValidException() throws Exception {
    	Mockito.when(pservices.addProduct(Mockito.any(Product.class))).thenReturn(responseProduct);
    	requestProduct.setPname(null);
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/add/product").content(new ObjectMapper().writeValueAsString(requestProduct)).contentType(MediaType.APPLICATION_JSON_VALUE);
    	 mockMvc.perform(requestBuilder).andExpect(result->Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)).andReturn();
    }
    
    
    @Test
    @DisplayName("Product list can be returned")
    public void getProduct_shouldReturnProductList() throws Exception {
    	List<Product> res= new ArrayList<>();
    	res.add(responseProduct);
    	Mockito.when(pservices.getAllProduct(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(res);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/get/products").contentType(MediaType.APPLICATION_JSON_VALUE);
    	MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isAccepted()).andReturn();
    	String responseBody = mvcResult.getResponse().getContentAsString();
    	List<Product> response= new ObjectMapper().readValue(responseBody,new TypeReference<List<Product>>() {});
    	
    	Assertions.assertNotNull(response,"Product list is empty");
    	Mockito.verify(pservices,Mockito.times(1)).getAllProduct(Mockito.any(),Mockito.any() , Mockito.any(), Mockito.any());
    	
    	
    }
    
   
    @Test
    @DisplayName("Product can be removed")
    public void testRemoveProduct_shouldRemoveProduct() throws Exception {
    	Mockito.when(pservices.removeProduct(Mockito.anyInt())).thenReturn(responseProduct);
    	
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/product/{pid}",1).contentType(MediaType.APPLICATION_JSON_VALUE);
    	MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isAccepted()).andReturn();
    	String responseBody = mvcResult.getResponse().getContentAsString();
    	Product response = new ObjectMapper().readValue(responseBody, Product.class);
    	Mockito.verify(pservices,Mockito.times(1)).removeProduct(Mockito.anyInt());
    	Assertions.assertNotNull(response,"Product returned is null");
    }
    
    
    
	
}
