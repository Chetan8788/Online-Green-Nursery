package com.masai.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masai.dto.OrderReqDto;
import com.masai.dto.UpdateOrderDto;
import com.masai.exception.OrderException;
import com.masai.model.Order;
import com.masai.service.OrderService;

@RestController
@CrossOrigin("*")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/orders")
	public ResponseEntity<Order> addOrder(@Valid @RequestBody OrderReqDto orderReqDto) throws OrderException {

		return new ResponseEntity<Order>(orderService.addOrder(orderReqDto), HttpStatus.OK);
	}

	@PutMapping("/orders")
	public ResponseEntity<Order> updateOrder(@Valid @RequestBody UpdateOrderDto updateOrderDto) throws OrderException {

		Order updatedOrder = orderService.updateOrder(updateOrderDto);

		return new ResponseEntity<Order>(updatedOrder, HttpStatus.OK);

	}

	@DeleteMapping("/orders/{orderId}")
	public ResponseEntity<Order> deleteOrder(@PathVariable("orderId") Integer orderId) throws OrderException {

		Order deletedOrder = orderService.deleteOrder(orderId);

		return new ResponseEntity<Order>(deletedOrder, HttpStatus.OK);

	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Order> viewOrder(@PathVariable("orderId") Integer orderId) throws OrderException {

		Order order = orderService.viewOrder(orderId);

		return new ResponseEntity<Order>(order, HttpStatus.OK);

	}

}
