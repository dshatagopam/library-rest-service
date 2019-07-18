package com.library.rest.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.Checkout;
import com.library.service.CheckoutService;

@Validated
@RestController
@RequestMapping("/v1/checkouts")
public class CheckoutController {
	@Autowired
	private CheckoutService checkoutService;

	@PostMapping(consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Checkout> createCheckout(@Valid @RequestBody Checkout checkout) {
		try {
			Checkout response = checkoutService.save(checkout);
			return new ResponseEntity<Checkout>(response, new HttpHeaders(), HttpStatus.CREATED);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
    }
	
	@GetMapping(path = "/{id}", produces = "application/json")
    public @ResponseBody Checkout getCheckout(@PathVariable Long id) {
    	return checkoutService.getCheckoutById(id);
    }
    
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Checkout>> getCheckouts() {
        List<Checkout> checkouts = checkoutService.getAllCheckouts();
        return new ResponseEntity<List<Checkout>>(checkouts, new HttpHeaders(), HttpStatus.OK);
    }
    
    @PatchMapping(path = "/{id}", headers="Accept=application/json")
    public ResponseEntity<Checkout> updateCheckout(@RequestBody Map<String,Boolean> update, 
    		@PathVariable Long id) {
		 Boolean isReturned = update.get("isReturned");
		 if (BooleanUtils.isTrue(isReturned)) {
			 try {
				 Checkout updated = checkoutService.updateCheckout(id, isReturned);
				 return new ResponseEntity<Checkout>(updated, HttpStatus.OK);	
			 } catch(Exception e) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			 }
		}
		return new ResponseEntity<Checkout>(HttpStatus.NO_CONTENT); 
    }
    

    @GetMapping(path = "/user/{id}", produces = "application/json")
    public ResponseEntity<List<Checkout>> getCheckoutByUser(@PathVariable Long id) {
    	List<Checkout> checkouts = checkoutService.getCheckoutsByUser(id);
        return new ResponseEntity<List<Checkout>>(checkouts, new HttpHeaders(), HttpStatus.OK);
    }

}
