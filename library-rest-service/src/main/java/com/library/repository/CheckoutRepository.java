package com.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.library.model.Checkout;

@RepositoryRestResource
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
	
	@Query("SELECT co FROM Checkout co WHERE co.userId=(:userId) AND co.returnedDate is null order by dueDate")
	List<Checkout> findAllCheckoutsByUserId(@Param("userId") Long userId);
	
	@Query("SELECT co FROM Checkout co WHERE co.returnedDate is null order by dueDate")
	List<Checkout> findAllCheckouts();
	
	@Query("SELECT co FROM Checkout co WHERE co.bookId=(:bookId) AND co.returnedDate is not null order by dueDate")
	List<Checkout> findCheckoutHistoryForBookId(@Param("bookId") Long bookId);
}
