package com.masai.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;
import com.masai.exception.OrderException;
import com.masai.model.Order;
import com.masai.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
    private OrderService orderService;
	
	@PostMapping("/orders")
	public ResponseEntity<Order> addOrder(@Valid @RequestBody  Order order) throws OrderException {
		
		Order savedOrder=orderService.addOrder(order);
		
		return new ResponseEntity<Order>(savedOrder,HttpStatus.ACCEPTED);
		
	}
	
	@PutMapping("/orders")
	public ResponseEntity<Order> updateOrder(@Valid @RequestBody  Order order) throws OrderException {
		
        Order updatedOrder=orderService.updateOrder(order);
		
		return new ResponseEntity<Order>(updatedOrder,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/orders/{orderId}")
	public ResponseEntity<Order> deleteOrder(@PathVariable("orderId")  Integer orderId) throws OrderException {
		
        Order deletedOrder=orderService.deleteOrder(orderId);
		
		return new ResponseEntity<Order>(deletedOrder,HttpStatus.OK);
		
	}
	
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Order> viewOrder(@PathVariable("orderId") Integer orderId) throws OrderException {
		
        Order order=orderService.viewOrder(orderId);
		
		return new ResponseEntity<Order>(order,HttpStatus.OK);
		
	}
	
//	@GetMapping
//	public ResponseEntity<List<Order>> getAllOrders() throws OrderException {
//		List<Order> orders=orderService.viewAllOrders();
//		return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
//		
//	}

}
