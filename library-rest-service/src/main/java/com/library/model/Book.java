package com.library.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter @Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "book")
public class Book {
	
	@Id
	private Long id;
	private String title;
	private String author;
	private String isbn = RandomStringUtils.randomNumeric(13);
	
	public Book() {}
}
