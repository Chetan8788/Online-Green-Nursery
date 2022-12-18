package com.masai.service;

import java.util.List;

import com.masai.dto.OrderReqDto;
import com.masai.dto.UpdateOrderDto;
import com.masai.exception.OrderException;
import com.masai.model.Order;

public interface OrderService {

	public Order addOrder(OrderReqDto orderReqDto) throws OrderException;

	public Order updateOrder(UpdateOrderDto updateOrderDto) throws OrderException;

	public Order deleteOrder(Integer orderId) throws OrderException;

	public Order viewOrder(Integer orderId) throws OrderException;

	public List<Order> viewAllOrders() throws OrderException;

}
