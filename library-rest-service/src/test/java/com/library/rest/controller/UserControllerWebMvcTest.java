package com.library.rest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.hamcrest.CoreMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.library.model.User;
import com.library.service.BookService;
import com.library.service.CheckoutService;
import com.library.service.UserService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class UserControllerWebMvcTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private BookService bookService;
	
	@MockBean
    private UserService userService;
	
	@MockBean
    private CheckoutService checkoutService;
	
	/*
    	Trying to get the user by providing valid user id
   */
   @Test
   public void testGetUserById() throws Exception {

       Long userId = Long.valueOf(1);
       String name = "John Doe";

       //mock response
       User user = new User(userId, name) ;

       //mocking the bean
       Mockito.when(userService.getUserById(userId)).thenReturn(user);

       //response is retrieved as MvcResult
       mockMvc.perform(get("/v1/users/{id}", userId)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("$.id", CoreMatchers.is(userId.intValue())))
       		   .andExpect(jsonPath("$.name", CoreMatchers.is(name)))
       		   .andDo(document("get-user-by-id"));
   }


   /*
	Trying to get all user
	*/
	@Test
	public void testGetUsers() throws Exception {
	
	  Long userId1 = Long.valueOf(1000);
	  String name1 = "Jane Smith";
	  User user1 = new User(userId1, name1);
	  
	  Long userId2 = Long.valueOf(2000);
	  String name2 = "Foo Bar";
	  User user2 = new User(userId2, name2);
	  
	  List<User> users = Stream.of(user1, user2).collect(Collectors.toList());
	  
	  //mocking the bean
	  Mockito.when(userService.getAllUsers(0, 10, "id")).thenReturn(users);
	
	  //response is retrieved as MvcResult
	  mockMvc.perform(get("/v1/users")
	          .accept(MediaType.APPLICATION_JSON))
	          .andExpect(status().isOk())
	          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	          .andExpect(jsonPath("$").exists())
	  		  .andDo(document("get-users"));
	}

}
