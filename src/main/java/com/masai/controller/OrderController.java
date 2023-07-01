package com.masai.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.dto.OrderReqDto;
import com.masai.dto.UpdateOrderDto;
import com.masai.exception.OrderException;
import com.masai.model.Order;
import com.masai.service.OrderService;
import com.masai.service.UserHelper;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserHelper userHelper;

	@PreAuthorize("hasRole('USER')")
	@PostMapping("")
	public ResponseEntity<Order> addOrder(@Valid @RequestBody OrderReqDto orderReqDto) throws OrderException {
		logger.info("Adding new order");

		Integer userId = userHelper.getLoggedInUserId();
		Order addedOrder = orderService.addOrder(orderReqDto, userId);

		logger.info("New order added successfully");
		return new ResponseEntity<>(addedOrder, HttpStatus.OK);
	}

	@PutMapping("")
	public ResponseEntity<Order> updateOrder(@Valid @RequestBody UpdateOrderDto updateOrderDto) throws OrderException {
		logger.info("Updating order");

		Integer userId = userHelper.getLoggedInUserId();

		if (!userId.equals(updateOrderDto.getUserId()))
			throw new OrderException("You can't update others' order");

		Order updatedOrder = orderService.updateOrder(updateOrderDto);

		logger.info("Order updated successfully");
		return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Order> deleteOrder(@PathVariable("orderId") Integer orderId) throws OrderException {
		logger.info("Deleting order");

		if (userHelper.getLoggedInUserId().equals(orderService.viewOrder(orderId).getUser().getUserId()))
			throw new OrderException("You can't delete orders with others' ID");

		Order deletedOrder = orderService.deleteOrder(orderId);

		logger.info("Order deleted successfully");
		return new ResponseEntity<>(deletedOrder, HttpStatus.OK);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> viewOrder(@PathVariable("orderId") Integer orderId) throws OrderException {
		logger.info("Fetching order");

		if (userHelper.getLoggedInUserId().equals(orderService.viewOrder(orderId).getUser().getUserId()))
			throw new OrderException("You can't view orders with others' ID");

		Order order = orderService.viewOrder(orderId);

		logger.info("Order fetched successfully");
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
}
