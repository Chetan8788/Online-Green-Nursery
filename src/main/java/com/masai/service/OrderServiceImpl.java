package com.masai.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.masai.exception.OrderException;
import com.masai.model.Order;
import com.masai.repository.OrderDao;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDao orderDao;

	@Override
	public Order addOrder(Order order) throws OrderException {
		
		Order savedOrder=orderDao.save(order);
		if(savedOrder!=null) {
			return savedOrder;
		}else throw new OrderException("Order couldn't add");
	
		
	}

	@Override
	public Order updateOrder(Order order) throws OrderException {
		
		Optional<Order> optional=orderDao.findById(order.getOrderId());
		if(optional.isPresent()) {
			Order existingOrder=optional.get();
			orderDao.save(order);
			return existingOrder;
		}else throw new OrderException("Order not found with orderId : "+order.getOrderId());
	
		
	}

	@Override
	public Order deleteOrder(Integer orderId) throws OrderException {
		
		Optional<Order> optional=orderDao.findById(orderId);
		
		if(optional.isPresent()) {
			
			Order order=optional.get();
			orderDao.deleteById(orderId);
			return order;
			
		}else throw new OrderException("Order not found with orderId : "+orderId);
		
	}

	@Override
	public Order viewOrder(Integer orderId) throws OrderException {
		
		return  orderDao.findById(orderId).orElseThrow(()->new OrderException("Order not found with order id : "+orderId));
		
	}

	@Override
	public List<Order> viewAllOrders() throws OrderException {
		
		List<Order> orders=orderDao.findAll();
		if(orders!=null) {
			return orders;
		}else throw new OrderException("Order not found !");

	}

}
