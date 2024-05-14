package com.wms.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wms.entity.User;
import com.wms.entity.UserStatus;
import com.wms.service.UserService;

@WebMvcTest(controllers=UserController.class)
@AutoConfigureMockMvc(addFilters=false)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService uservice;
	
	@MockBean
	private PasswordEncoder pencoder;
	
	private Authentication auth;
	private User requestUser;
	private User responseUser;
	
	
	@BeforeEach
	public void init() {
		requestUser= new User();
		requestUser.setName("Ankit");
		requestUser.setEmail("akt00071000@gmail.com");
		requestUser.setLocation("Delhi");
		requestUser.setMobno("1234567895");
		requestUser.setPassword("MyTestPassword");
		requestUser.setStatus(UserStatus.Active);
		
		
		responseUser= new User();
		responseUser.setUserId(1);
		responseUser.setName("Ankit");
		responseUser.setEmail("akt00071000@gmail.com");
		responseUser.setLocation("Delhi");
		responseUser.setMobno("1234567895");
		responseUser.setPassword("MyTestPassword");
		responseUser.setStatus(UserStatus.Active);
		
		auth= new UsernamePasswordAuthenticationToken("akt00071000@gmail.com","MyTestPassword");
		
	}
	

 @Test
 @DisplayName("User can be added")
 public void testSignUp_Success() throws Exception{
	 Mockito.when(pencoder.encode(Mockito.anyString())).thenReturn(responseUser.getPassword());
	 Mockito.when(uservice.registerUser(Mockito.any(User.class))).thenReturn(responseUser);
	 
	
	 String jsonPayload = "{\"email\":\"" + requestUser.getEmail() + "\",\"name\":\"" + requestUser.getName() + "\",\"password\":\"" + requestUser.getPassword() + "\",\"mobno\":\"" + requestUser.getMobno() + "\",\"location\":\"" + requestUser.getLocation() + "\"}";
	 RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/signUp").content(jsonPayload).contentType(MediaType.APPLICATION_JSON_VALUE);
	 MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
	 
	 String responseBody = mvcResult.getResponse().getContentAsString();
	 User response= new ObjectMapper().readValue(responseBody, User.class);
	 
	 Assertions.assertNotNull(response,"User cannot be null");
	 Assertions.assertEquals(1, response.getUserId(),"User id should be equal to 1");
	 
	 Mockito.verify(uservice,Mockito.times(1)).registerUser(Mockito.any(User.class));
	 
	 
 }
 
 
 
 @Test
 @DisplayName("When invalid  name passed Exception can be thrown")
 public void testSignUp_WhenInvalidNamePassedExceptionshouldBeThrown() throws Exception{
	
	 
	  requestUser.setPassword("");
	 String jsonPayload = "{\"email\":\"" + requestUser.getEmail() + "\",\"name\":\"" + requestUser.getName() + "\",\"password\":\"" + requestUser.getPassword() + "\",\"mobno\":\"" + requestUser.getMobno() + "\",\"location\":\"" + requestUser.getLocation() + "\"}";
	 RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/signUp").content(jsonPayload).contentType(MediaType.APPLICATION_JSON_VALUE);
	 mockMvc.perform(requestBuilder).andExpect(result->Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)).andReturn();
	
	 
	 
 }
 
 @Test
 @DisplayName("When invalid  email passed Exception can be thrown")
 public void testSignUp_WhenInvalidemailssedExceptionshouldBeThrown() throws Exception{
	
	 
	  requestUser.setEmail("");
	 String jsonPayload = "{\"email\":\"" + requestUser.getEmail() + "\",\"name\":\"" + requestUser.getName() + "\",\"password\":\"" + requestUser.getPassword() + "\",\"mobno\":\"" + requestUser.getMobno() + "\",\"location\":\"" + requestUser.getLocation() + "\"}";
	 RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/signUp").content(jsonPayload).contentType(MediaType.APPLICATION_JSON_VALUE);
	 mockMvc.perform(requestBuilder).andExpect(result->Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)).andReturn();
	
	 
	 
 }
 
 
 
 
 @Test
 @DisplayName("User can login succesfully")
 public void testLogIn_Success() throws Exception{
	 Mockito.when(uservice.getUserDetails(Mockito.anyString())).thenReturn(responseUser);
	 RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/logIn").principal(auth).contentType(MediaType.APPLICATION_JSON_VALUE);
	 MvcResult mvcResult= mockMvc.perform(requestBuilder).andExpect(status().isAccepted()).andReturn();
	 
	 
	 String response= mvcResult.getResponse().getContentAsString();
	 
	 Assertions.assertNotNull(response,"message cannot be null");
	
	 Mockito.verify(uservice,Mockito.times(1)).getUserDetails(Mockito.anyString());
	 
	 
	 
 }
 
 
 
	

}
