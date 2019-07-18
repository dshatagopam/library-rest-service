package com.library.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.User;
import com.library.service.UserService;

@Validated
@RestController
@RequestMapping("/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping(path = "/{id}", produces = "application/json")
    public @ResponseBody User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers(
                        @RequestParam(defaultValue = "0") Integer pageNo,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(defaultValue = "id") String sortBy) {
    	
        List<User> users = userService.getAllUsers(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<User>>(users, new HttpHeaders(), HttpStatus.OK);
    }

}
