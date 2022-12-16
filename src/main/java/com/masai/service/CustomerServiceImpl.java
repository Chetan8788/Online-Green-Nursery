package com.masai.service;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CustomerException;
import com.masai.model.Comment;
import com.masai.model.Customer;
import com.masai.model.Order;
import com.masai.repository.CustomerDao;

@Service
public class CustomerServiceImpl implements CustomerService{
	
    @Autowired
	private CustomerDao customerDao;
    
	@Override
	public Customer registerCustomer(Customer customer) throws CustomerException {
		
		Set<Order> orders=customer.getOrders() ;
		for(Order o:orders) {
			o.setCustomer(customer);
		}

		 return customerDao.save(customer); 
	}

	@Override
	public Customer getCustomerByEmail(String email) throws CustomerException {
		Customer customer = customerDao.findByEmail(email);
		if(customer==null)throw new CustomerException("customer not found with email "+email);
		else return customer;
	}

	

}
