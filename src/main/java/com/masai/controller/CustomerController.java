package com.masai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.CustomerException;
import com.masai.model.Customer;
import com.masai.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customers")
	public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody Customer customer) throws CustomerException {
		Customer registeredCustomer=customerService.registerCustomer(customer);
		return new  ResponseEntity<Customer>(registeredCustomer,HttpStatus.CREATED);
	}
	
	@GetMapping("/customers/{email}")
	public ResponseEntity<List<Customer>> getCustomerByEmail(@PathVariable("email") String email) throws CustomerException {
		
         List<Customer> customers=customerService.getAllCustomer();
		
		return new ResponseEntity<List<Customer>>(customers,HttpStatus.OK);
	}
	
	@PutMapping("/customers")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer) throws CustomerException {
		Customer existingCustomer=customerService.updateCustomer(customer);
		return new  ResponseEntity<Customer>(existingCustomer,HttpStatus.OK);
	}
	
	@DeleteMapping("/customers/{customerId}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") Integer customerId) throws CustomerException {		
		
		Customer customer=customerService.deleteCustomer(customerId);
		
		return new  ResponseEntity<Customer>(customer,HttpStatus.OK);
		
	}
	
	@GetMapping("/getcustomers/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId")  Integer customerId) throws CustomerException {
		
        Customer customer=customerService.getCustomerById(customerId);
		
		return new  ResponseEntity<Customer>(customer,HttpStatus.OK);
	}
	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomer() throws CustomerException {
		
		List<Customer> customers=customerService.getAllCustomer();
		
		return new ResponseEntity<List<Customer>>(customers,HttpStatus.OK);
	}

}
