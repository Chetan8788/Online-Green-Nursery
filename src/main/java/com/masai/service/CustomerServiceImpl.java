package com.masai.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CustomerException;
import com.masai.model.Customer;
import com.masai.model.Order;
import com.masai.repository.CustomerDao;

@Service
public class CustomerServiceImpl implements CustomerService{
	
    @Autowired
	private CustomerDao customerDao;
    
	@Override
	public Customer registerCustomer(Customer customer) throws CustomerException {
		
//		Set<Order> orders=customer.getOrders() ;
//		for(Order o:orders) {
//			o.setCustomer(customer);
//		}

		 return customerDao.save(customer); 
	}

	@Override
	public Customer getCustomerByEmail(String email) throws CustomerException {
		 Customer customer= customerDao.findByEmail(email);
		if(customer==null)throw new CustomerException("customer not found with email "+email);
		else return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer) throws CustomerException {
		
		Optional<Customer> optional=customerDao.findById(customer.getCustomerId());
		if(optional.isPresent()) {
			Customer existingCustomer =optional.get();
			
			customerDao.save(customer);
			
			return existingCustomer;
		}else throw new CustomerException("Customer not exist with customer Id : "+customer.getCustomerId());
		
	}

	@Override
	public Customer deleteCustomer(Integer customerId) throws CustomerException {
		
		Optional<Customer> optional=customerDao.findById(customerId);
		
		if(optional.isPresent()) {
			Customer existingCustomer =optional.get();
			
			customerDao.deleteById(customerId);	
			
			return existingCustomer;
		}else throw new CustomerException("Customer not exist with customer Id : "+customerId);
		
	
	}

	@Override
	public Customer getCustomerById(Integer customerId) throws CustomerException {
		
       Optional<Customer> optional=customerDao.findById(customerId);
		
		if(optional.isPresent()) {
						
			return optional.get();
			
		}else throw new CustomerException("Customer not exist with customer Id : "+customerId);
		
	}

	@Override
	public List<Customer> getAllCustomer() throws CustomerException {
		
		List<Customer> customers=customerDao.findAll();
		
		if(customers!=null) {
			return customers;
		}else throw new CustomerException(" Customer Not Found !");
		
		
	}

	

}
