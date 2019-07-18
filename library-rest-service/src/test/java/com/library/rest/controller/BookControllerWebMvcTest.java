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

import com.library.model.Book;
import com.library.service.BookService;
import com.library.service.CheckoutService;
import com.library.service.UserService;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.RandomStringUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class BookControllerWebMvcTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private BookService bookService;
	
	@MockBean
    private UserService userService;
	
	@MockBean
    private CheckoutService checkoutService;

	/*
    	Trying to get the book by providing valid book id
   */
   @Test
   public void testGetBookById() throws Exception {

       Long bookId = Long.valueOf(1);
       String title = "The Jungle Book";
       String author = "Rudyard Kipling";
       String isbn = RandomStringUtils.randomNumeric(13);

       //mock response
       Book book = new Book(bookId, title, author, isbn) ;

       //mocking the bean
       Mockito.when(bookService.getById(bookId)).thenReturn(book);


       //response is retrieved as MvcResult
       mockMvc.perform(get("/v1/books/{id}", bookId)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("$.id", CoreMatchers.is(bookId.intValue())))
       		   .andExpect(jsonPath("$.title", CoreMatchers.is(title)))
       		   .andExpect(jsonPath("$.author", CoreMatchers.is(author)))
       		   .andExpect(jsonPath("$.isbn", CoreMatchers.is(isbn)))
       		   .andDo(document("get-book-by-id"));
   }


   /*
	Trying to get all books
	*/
	@Test
	public void testGetBooks() throws Exception {
	
	  Long bookId1 = Long.valueOf(1000);
	  String title1 = "The Jungle Book";
	  String author1 = "Rudyard Kipling";
	  String isbn1 = RandomStringUtils.randomNumeric(13);
	  Book book1 = new Book(bookId1, title1, author1, isbn1);
	  
	  Long bookId2 = Long.valueOf(10);
	  String title2 = "The Odyssey";
	  String author2 = "Homer";
	  String isbn2 = RandomStringUtils.randomNumeric(13);
	  Book book2 = new Book(bookId2, title2, author2, isbn2);
	  
	  List<Book> books = Stream.of(book1, book2).collect(Collectors.toList());
	  
	  //mocking the bean
	  Mockito.when(bookService.getAllBooks(1,2,"title")).thenReturn(books);
	
	  //response is retrieved as MvcResult
	  mockMvc.perform(get("/v1/books?page=1&size=2&sortBy=title")
	          .accept(MediaType.APPLICATION_JSON))
	          .andExpect(status().isOk())
	          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	          .andExpect(jsonPath("$").exists())
	  		  .andDo(document("get-books-sort-by-title"));
	}
	
	/*
	Trying to get all books
	*/
	@Test
	public void testGetBooks_FilterBy_Author() throws Exception {
	
	  Long bookId1 = Long.valueOf(1000);
	  String title1 = "The Jungle Book";
	  String author1 = "Rudyard Kipling";
	  String isbn1 = RandomStringUtils.randomNumeric(13);
	  Book book1 = new Book(bookId1, title1, author1, isbn1);
	  
	  Long bookId2 = Long.valueOf(10);
	  String title2 = "The Odyssey";
	  String author2 = "Homer";
	  String isbn2 = RandomStringUtils.randomNumeric(13);
	  Book book2 = new Book(bookId2, title2, author2, isbn2);
	  
	  List<Book> books = Stream.of(book2).collect(Collectors.toList());
	  
	  //mocking the bean
	  Mockito.when(bookService.getBooksFilterBy(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
			  ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(books);
	
	  //response is retrieved as MvcResult
	  mockMvc.perform(get("/v1/books/filter?author=Homer")
	          .accept(MediaType.APPLICATION_JSON))
	          .andExpect(status().isOk())
	          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	          .andExpect(jsonPath("$").exists())
	  		  .andDo(document("get-books-filter-by-author"));
	}
}
