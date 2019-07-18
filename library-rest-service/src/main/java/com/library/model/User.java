package com.library.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "user")
public class User {
	
	@Id
	private Long id;
	private String name;
	
	public User() {}
}
