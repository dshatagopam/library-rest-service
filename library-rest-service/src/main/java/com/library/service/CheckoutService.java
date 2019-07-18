package com.library.service;

import java.util.List;

import com.library.exception.BookNotFoundException;
import com.library.exception.CheckoutNotFoundException;
import com.library.exception.UserNotFoundException;
import com.library.model.Checkout;

public interface CheckoutService {
	
	public Checkout save(Checkout checkout) throws BookNotFoundException, UserNotFoundException;
	
	public Checkout getCheckoutById(Long id);
	
	public List<Checkout> getAllCheckouts();
	
	public List<Checkout> getCheckoutsByUser(Long userId);
	
	public List<Checkout> getCheckoutHistoryForBook(Long bookId);
	
	public Checkout updateCheckout(Long id, Boolean isReturned) throws CheckoutNotFoundException;

}
