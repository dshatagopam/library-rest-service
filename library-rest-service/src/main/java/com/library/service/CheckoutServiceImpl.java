package com.library.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.exception.BookNotFoundException;
import com.library.exception.CheckoutNotFoundException;
import com.library.exception.UserNotFoundException;
import com.library.model.Checkout;
import com.library.repository.BookRepository;
import com.library.repository.CheckoutRepository;
import com.library.repository.UserRepository;

@Service
public class CheckoutServiceImpl implements CheckoutService {
	
	@Autowired
	private CheckoutRepository checkoutRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;

	
	@Override
	public Checkout save(Checkout checkout) throws BookNotFoundException, UserNotFoundException {
		bookRepository.findById(checkout.getBookId())
		.orElseThrow(() -> new BookNotFoundException(checkout.getBookId()));
		userRepository.findById(checkout.getUserId())
		.orElseThrow(() -> new UserNotFoundException(checkout.getUserId()));
		
		return checkoutRepository.save(checkout);
	}
	
	@Override
	public Checkout updateCheckout(Long id, Boolean isReturned) throws CheckoutNotFoundException {
		Checkout checkout = checkoutRepository.findById(id)
		.orElseThrow(() -> new CheckoutNotFoundException(id));
		
		checkout.setReturnedDate(LocalDate.now());
		
		return checkoutRepository.save(checkout);
	}

	@Override
	public Checkout getCheckoutById(Long id) {
		return checkoutRepository.getOne(id);
	}

	@Override
	public List<Checkout> getAllCheckouts() {
		return checkoutRepository.findAllCheckouts();
	}
	
	@Override
	public List<Checkout> getCheckoutsByUser(Long userId) {
		if(userId != null) {
			return checkoutRepository.findAllCheckoutsByUserId(userId);
		}
		else {
			return checkoutRepository.findAllCheckouts();
		}
	}

	@Override
	public List<Checkout> getCheckoutHistoryForBook(Long bookId) {
		return checkoutRepository.findCheckoutHistoryForBookId(bookId);
	}
	
	

}
