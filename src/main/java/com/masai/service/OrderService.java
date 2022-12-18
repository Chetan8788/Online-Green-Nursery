package com.masai.service;

import java.util.List;

import com.masai.exception.OrderException;
import com.masai.model.Orders;

public interface OrderService {
	Orders addOrders(Orders orders) throws OrderException;

	Orders deleteOrders(Integer id) throws OrderException;

	Orders updateOrders(Orders orders) throws OrderException;

	Orders viewOrders(Integer id) throws OrderException;

	List<Orders> viewAllOrders() throws OrderException;

}
