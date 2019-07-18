package com.library.exception;

public class CheckoutNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -5106666227779814336L;

	public CheckoutNotFoundException(Long id) {
        super("Checkout id not found : " + id);
    }


}
