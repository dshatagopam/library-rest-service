package com.library.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7521438952454663505L;

	public UserNotFoundException(Long id) {
        super("User id not found : " + id);
    }

}
