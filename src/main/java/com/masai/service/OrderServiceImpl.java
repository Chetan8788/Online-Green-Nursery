package com.masai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.masai.exception.OrderException;
import com.masai.model.Orders;
import com.masai.repository.OrderDao;

public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderDao orderDao;

	@Override
	public Orders addOrders(Orders orders) throws OrderException {
		if (orders == null)
			throw new OrderException("Please provide valid data");
		return orderDao.save(orders);

	}

	@Override
	public Orders deleteOrders(Integer id) throws OrderException {

		Orders foundOrders = orderDao.findById(id).orElseThrow(() -> new OrderException("Order not Found"));
		orderDao.delete(foundOrders);
		return foundOrders;
	}

	@Override
	public Orders updateOrders(Orders orders) throws OrderException {
		if (orders == null)
			throw new OrderException("Please provide valid data");
		Orders foundOrders = orderDao.findById(orders.getOrderId())
				.orElseThrow(() -> new OrderException("Order not Found"));

		return orderDao.save(orders);
	}

	@Override
	public Orders viewOrders(Integer id) throws OrderException {

		return orderDao.findById(id).orElseThrow(() -> new OrderException("Order not Found"));
	}

	@Override
	public List<Orders> viewAllOrders() throws OrderException {
		List<Orders> orders = orderDao.findAll();
		if (orders.isEmpty())
			throw new OrderException("No record found");
		return orders;
	}

}
