package com.library.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.Book;
import com.library.model.Checkout;
import com.library.service.BookService;
import com.library.service.CheckoutService;


@Validated
@RestController
@RequestMapping("/v1/books")
public class BookController {

	@Autowired
    private BookService bookService;
	
	@Autowired
    private CheckoutService checkoutService;
	
	
	@GetMapping(path = "/{id}", produces = "application/json")
    public @ResponseBody Book getBook(@PathVariable Long id) {
        return bookService.getById(id);
    }
	
	@GetMapping(path = "/{id}/checkouts", produces = "application/json")
    public ResponseEntity<List<Checkout>> getCheckoutHistory(@PathVariable Long id) {
        List<Checkout> checkouts = checkoutService.getCheckoutHistoryForBook(id);
        return new ResponseEntity<List<Checkout>>(checkouts, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Book>> getAllBooks(
    		@RequestParam(defaultValue = "0") Integer page,
    		@RequestParam(defaultValue = "20") Integer size,
    		@RequestParam(defaultValue = "id") String sortBy) {
    	
        List<Book> books = bookService.getAllBooks(page, size, sortBy);
        return new ResponseEntity<List<Book>>(books, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping(path = "/filter", produces = "application/json")
    public ResponseEntity<List<Book>> getBooksFilterBy(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn) {
    	
    	try {
    		List<Book> books = bookService.getBooksFilterBy(id, title, author, isbn);
    		return new ResponseEntity<List<Book>>(books, new HttpHeaders(), HttpStatus.OK);
    	} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	}
    }

    
}