package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.library.model.Book;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Long>{

}
