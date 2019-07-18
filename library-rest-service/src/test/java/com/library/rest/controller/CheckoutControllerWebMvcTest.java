package com.library.rest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.model.Checkout;
import com.library.service.BookService;
import com.library.service.CheckoutService;
import com.library.service.UserService;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@WebMvcTest(CheckoutController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class CheckoutControllerWebMvcTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
    private BookService bookService;
	
	@MockBean
    private UserService userService;
	
	@MockBean
    private CheckoutService checkoutService;

	@Test
   public void testCreateCheckout() throws Exception {
		Long checkoutId = Long.valueOf(1);
        Long userId = Long.valueOf(100);
        Long bookId = Long.valueOf(20);

        //mock request/response 
        Checkout checkout = new Checkout();
        checkout.setId(checkoutId);
        checkout.setBookId(bookId);
        checkout.setUserId(userId);

        Mockito.when(checkoutService.save(ArgumentMatchers.any(Checkout.class))).thenReturn(checkout);

        mockMvc.perform(post("/v1/checkouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkout)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", CoreMatchers.is(checkoutId.intValue())))
                .andExpect(jsonPath("$.bookId", CoreMatchers.is(bookId.intValue())))
                .andExpect(jsonPath("$.userId", CoreMatchers.is(userId.intValue())))
                .andExpect(jsonPath("$.dueDate", CoreMatchers.notNullValue()))       		   
                .andDo(document("create-checkout"));
	 }
	
	@Test
	 public void testGetCheckouts() throws Exception {
		Long checkoutId1 = Long.valueOf(1);
        Long userId1 = Long.valueOf(100);
        Long bookId1 = Long.valueOf(20);

        Checkout c1 = new Checkout();
        c1.setId(checkoutId1);
        c1.setBookId(bookId1);
        c1.setUserId(userId1);
        
        Long checkoutId2 = Long.valueOf(2);
        Long userId2 = Long.valueOf(200);
        Long bookId2 = Long.valueOf(40);
        Checkout c2 = new Checkout();
        c2.setId(checkoutId2);
        c2.setBookId(bookId2);
        c2.setUserId(userId2);
		  
		  List<Checkout> checkouts = Stream.of(c1, c2).collect(Collectors.toList());
		  
		  //mocking the bean
		  Mockito.when(checkoutService.getAllCheckouts()).thenReturn(checkouts);
		
		  //response is retrieved as MvcResult
		  mockMvc.perform(get("/v1/checkouts")
		          .accept(MediaType.APPLICATION_JSON))
		          .andExpect(status().isOk())
		          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		          .andExpect(jsonPath("$").exists())
		  		  .andDo(document("get-checkouts"));
	}
	
	@Test
	 public void testGetCheckoutsByUser() throws Exception {
		Long checkoutId1 = Long.valueOf(1);
       Long userId1 = Long.valueOf(100);
       Long bookId1 = Long.valueOf(20);

       Checkout c1 = new Checkout();
       c1.setId(checkoutId1);
       c1.setBookId(bookId1);
       c1.setUserId(userId1);
       
       Long checkoutId2 = Long.valueOf(2);
       Long bookId2 = Long.valueOf(40);
       Checkout c2 = new Checkout();
       c2.setId(checkoutId2);
       c2.setBookId(bookId2);
       c2.setUserId(userId1);
		  
		  List<Checkout> checkouts = Stream.of(c1, c2).collect(Collectors.toList());
		  
		  //mocking the bean
		  Mockito.when(checkoutService.getCheckoutsByUser(userId1)).thenReturn(checkouts);
		
		  //response is retrieved as MvcResult
		  mockMvc.perform(get("/v1/checkouts/user/100")
		          .accept(MediaType.APPLICATION_JSON))
		          .andExpect(status().isOk())
		          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		          .andExpect(jsonPath("$").exists())
		  		  .andDo(document("get-checkouts-by-user"));
	}
	
	@Test
	public void updateCheckout() throws Exception
	{
		Checkout checkout = new Checkout(1L, 100L, 200L, LocalDate.now(), LocalDate.now());
		
	  Map<String,Boolean> map = new HashMap<String,Boolean>();
	  map.put("isReturned", true);
	  
	  //mocking the bean
	  Mockito.when(checkoutService.updateCheckout(ArgumentMatchers.anyLong(), ArgumentMatchers.anyBoolean()))
			  .thenReturn(checkout);
	  
	  mockMvc.perform(patch("/v1/checkouts/1")
          .content(objectMapper.writeValueAsString(map))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
          .andExpect(jsonPath("$.returnedDate", CoreMatchers.notNullValue()))       		   
	      .andDo(document("update-checkout"));;
	}
}
	
