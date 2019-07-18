package com.library.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.library.exception.BookNotFoundException;
import com.library.model.Book;
import com.library.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Book> save(List<Book> books) {
		return bookRepository.saveAll(books);
	}
	
	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}
	
	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book getById(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException(id));
	}
	
	@Override
    public List<Book> getBooksFilterBy(String id, String title, String author, String isbn) {
		Book book = new Book();
		try {
			if(StringUtils.isNotBlank(id)) {
				book.setId(Long.valueOf(id));
			}
			book.setTitle(title);
			book.setAuthor(author);
			book.setIsbn(isbn);
				
			ExampleMatcher matcher = ExampleMatcher.matchingAny()
				 .withIgnoreNullValues()
				 .withIgnoreCase()
				 .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
			Example<Book> example = Example.of(book, matcher);
			return bookRepository.findAll(example);
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Invalid book id: " + id);
		}
    }
	
	@Override
    public List<Book> getAllBooks(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
 
        Page<Book> pagedResult = bookRepository.findAll(paging);
         
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Book>();
        }
    }
}
