package com.library.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "checkout")
public class Checkout {
	public static final int DEFAULT_CHECKOUT_DAYS = 7;
	
	@Id
	@GeneratedValue
	private Long id;
    
	@NotNull
	private Long bookId;
	
	@NotNull
	private Long userId;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future
	private LocalDate dueDate = LocalDate.now().plusDays(DEFAULT_CHECKOUT_DAYS);
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent
	private LocalDate returnedDate;
	
	public Checkout() {}
}