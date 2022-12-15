package com.masai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CustomerException;
import com.masai.model.Customer;
import com.masai.repository.CustomerDao;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
	private CustomerDao customerDao;
    
	@Override
	public Customer registerCustomer(Customer customer) throws CustomerException {
		Customer registeredCustomer=customerDao.save(customer);
		if(registeredCustomer==null) throw new CustomerException("Registration Fail !");
		else return registeredCustomer;
	}

}
