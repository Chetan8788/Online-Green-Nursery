package com.masai.controller;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.masai.auth.Authorization;
import com.masai.dto.OrderReqDto;
import com.masai.dto.UpdateOrderDto;
import com.masai.exception.OrderException;
import com.masai.model.Order;
import com.masai.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	Authorization authorization;

	@PostMapping("/orders")
	public ResponseEntity<Order> addOrder(@Valid @RequestBody OrderReqDto orderReqDto, @RequestHeader String token)
			throws OrderException {
		Integer id = authorization.isAuthorized(token, "customer");
		if (id != orderReqDto.getCustomerId())
			throw new OrderException("You can't place orders with orthers id");
		return new ResponseEntity<Order>(orderService.addOrder(orderReqDto), HttpStatus.OK);
	}

	@PutMapping("/orders")
	public ResponseEntity<Order> updateOrder(@Valid @RequestBody UpdateOrderDto updateOrderDto,
			@RequestHeader String token) throws OrderException {
		Integer id = authorization.isAuthorized(token, "customer");
		if (id != updateOrderDto.getCustomerId())
			throw new OrderException("You can't update orders with orthers id");
		Order updatedOrder = orderService.updateOrder(updateOrderDto);

		return new ResponseEntity<Order>(updatedOrder, HttpStatus.OK);

	}

	@DeleteMapping("/orders/{orderId}")
	public ResponseEntity<Order> deleteOrder(@PathVariable("orderId") Integer orderId, @RequestHeader String token)
			throws OrderException {
		Integer id = authorization.isAuthorized(token, "customer");
		Integer coid = orderService.viewOrder(orderId).getCustomer().getCustomerId();
		if (id != coid)
			throw new OrderException("You can't delete orders with orthers id");
		Order deletedOrder = orderService.deleteOrder(orderId);

		return new ResponseEntity<Order>(deletedOrder, HttpStatus.OK);

	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Order> viewOrder(@PathVariable("orderId") Integer orderId, @RequestHeader String token)
			throws OrderException {
		Integer id = authorization.isAuthorized(token, "customer");
		Integer coid = orderService.viewOrder(orderId).getCustomer().getCustomerId();
		if (id != coid)
			throw new OrderException("You can't view orders with orthers id");
		Order order = orderService.viewOrder(orderId);

		return new ResponseEntity<Order>(order, HttpStatus.OK);

	}

}
