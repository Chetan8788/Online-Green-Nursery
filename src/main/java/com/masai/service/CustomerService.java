package com.masai.service;

import com.masai.exception.CustomerException;
import com.masai.model.Customer;

public interface CustomerService {
 public Customer registerCustomer(Customer customer) throws CustomerException;
}