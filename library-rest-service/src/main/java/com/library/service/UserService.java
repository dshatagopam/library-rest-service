package com.library.service;

import java.util.List;

import com.library.model.User;

public interface UserService {
	public List<User> save(List<User> users) ;
	
	public User save(User user);
	
	public List<User> getAllUsers();
	
	public User getUserById(Long id);
	
	public List<User> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy);
}
