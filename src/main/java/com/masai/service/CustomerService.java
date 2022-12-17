package com.masai.service;

import java.util.List;

import com.masai.exception.CustomerException;
import com.masai.model.Customer;

public interface CustomerService {
	
 public Customer registerCustomer(Customer customer) throws CustomerException;

 public Customer getCustomerByEmail(String email)throws CustomerException;
 
 public Customer updateCustomer(Customer customer) throws CustomerException;
 
 public Customer deleteCustomer(Integer customerId) throws CustomerException;
 
 public Customer getCustomerById(Integer customerId) throws CustomerException;
 
 public List<Customer> getAllCustomer() throws CustomerException;

}
