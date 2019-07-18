package com.library.service;

import java.util.List;

import com.library.model.Book;

public interface BookService {
	public List<Book> save(List<Book> books);
	
	public Book save(Book book) ;
	
	public Book getById(Long id);
    
    public List<Book> getAllBooks();

	public List<Book> getAllBooks(Integer pageNumber, Integer pageSize, String sortBy);
	
    public List<Book> getBooksFilterBy(String id, String title, String author, String isbn);

}
