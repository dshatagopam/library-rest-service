package com.library;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.model.Book;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.UserService;

@SpringBootApplication
public class LibraryApplication {
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	CommandLineRunner loadUsers(UserService userService) {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json");
			try {
				List<User> users = mapper.readValue(inputStream,typeReference);
				userService.save(users);
				System.out.println("Loaded users.json into db");
			} catch (IOException e){
				System.out.println("Exception while saving User Info: " + e.getMessage());
			}
		};
	}
	
	@Bean
	CommandLineRunner loadBooks(BookService bookService) {
		return args -> {
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Book>> typeReference = new TypeReference<List<Book>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/books.json");
			try {
				List<Book> books = mapper.readValue(inputStream,typeReference);
				bookService.save(books);
				System.out.println("Loaded users.json into db");
			} catch (IOException e){
				System.out.println("Exception while saving Book Info: " + e.getMessage());
			}
		};
	}
}