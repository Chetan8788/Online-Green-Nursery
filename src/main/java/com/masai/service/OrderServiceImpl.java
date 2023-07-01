package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.dto.OrderReqDto;
import com.masai.dto.UpdateOrderDto;
import com.masai.exception.OrderException;
import com.masai.model.Order;
import com.masai.model.Planter;
import com.masai.model.User;
import com.masai.repository.OrderDao;
import com.masai.repository.PlanterDao;
import com.masai.repository.UserDao;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	PlanterDao planterDao;

	@Autowired
	UserDao userDao;
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	/**
	 * Adds a new order.
	 *
	 * @param orderReqDto The OrderReqDto object containing the order details.
	 * @param userId      The ID of the user placing the order.
	 * @return The created Order object.
	 * @throws OrderException if there is an error while adding the order or if the
	 *                        associated planter or user does not exist, or if there
	 *                        is not enough stock of the planter.
	 */
	@Override
	public Order addOrder(OrderReqDto orderReqDto, Integer userId) throws OrderException {
		// Find the planter associated with the order
		Planter planter = planterDao.findById(orderReqDto.getPlanterID())
				.orElseThrow(() -> new OrderException("Planter not found"));

		// Find the user placing the order
		User user = userDao.findById(userId).orElseThrow(() -> new OrderException("User not found"));

		// Check if there is enough stock of the planter
		Integer totalStock = planter.getStock();
		if (totalStock < orderReqDto.getQuantity()) {
			throw new OrderException("There is not enough stock");
		}

		// Update the stock of the planter
		planter.setStock(totalStock - orderReqDto.getQuantity());

		// Create a new order
		Order order = new Order();
		order.setUser(user);
		order.setOrderDate(LocalDateTime.now());
		order.getPlanters().add(planter);
		order.setTransactionMode(orderReqDto.getTransactionMode());
		order.setQuantity(orderReqDto.getQuantity());
		order.setTotalCost(orderReqDto.getQuantity() * planter.getCost());

		// Update the user's order list
		user.getOrders().add(order);

		// Save the order
		return orderDao.save(order);
	}

	/**
	 * Updates an existing order.
	 *
	 * @param updateOrderDto The UpdateOrderDto object containing the updated order
	 *                       details.
	 * @return The updated Order object.
	 * @throws OrderException if the order is not found with the given ID or if the
	 *                        associated planter or user does not exist, or if there
	 *                        is not enough stock of the planter.
	 */
	@Override
	public Order updateOrder(UpdateOrderDto updateOrderDto) throws OrderException {
		// Find the existing order to be updated
		Order orderPre = orderDao.findById(updateOrderDto.getOrderId())
				.orElseThrow(() -> new OrderException("Order not Found..."));

		// Retrieve the planter associated with the existing order
		Planter planterPre = orderPre.getPlanters().get(0);
		planterPre.setStock(planterPre.getStock() + orderPre.getQuantity());

		// Find the new planter for the updated order
		Planter planter = planterDao.findById(updateOrderDto.getPlanterID())
				.orElseThrow(() -> new OrderException("Planter not found"));

		// Find the customer associated with the order
		User customer = userDao.findById(updateOrderDto.getUserId())
				.orElseThrow(() -> new OrderException("User not found"));

		// Check if there is enough stock of the new planter
		Integer totalStock = planter.getStock();
		if (totalStock < updateOrderDto.getQuantity()) {
			throw new OrderException("There is not enough stock");
		}

		// Update the stock of the new planter
		planter.setStock(totalStock - updateOrderDto.getQuantity());

		// Create a new order with the updated details
		Order order = new Order();
		order.setOrderId(updateOrderDto.getOrderId());
		order.setUser(customer);
		order.setOrderDate(LocalDateTime.now());
		order.getPlanters().add(planter);
		order.setTransactionMode(updateOrderDto.getTransactionMode());
		order.setQuantity(updateOrderDto.getQuantity());
		order.setTotalCost(updateOrderDto.getQuantity() * planter.getCost());

		// Save the updated order
		return orderDao.save(order);
	}

	/**
	 * Deletes an order.
	 *
	 * @param orderId The ID of the order to be deleted.
	 * @return The deleted Order object.
	 * @throws OrderException if the order is not found with the given ID.
	 */
	@Override
	public Order deleteOrder(Integer orderId) throws OrderException {
		Optional<Order> optional = orderDao.findById(orderId);
		if (optional.isPresent()) {
			Order order = optional.get();
			orderDao.deleteById(orderId);
			return order;
		} else {
			throw new OrderException("Order not found with orderId: " + orderId);
		}
	}

	/**
	 * Retrieves an order by ID.
	 *
	 * @param orderId The ID of the order to be retrieved.
	 * @return The Order object.
	 * @throws OrderException if the order is not found with the given ID.
	 */
	@Override
	public Order viewOrder(Integer orderId) throws OrderException {
		logger.info("Viewing order with id {}", orderId);
		return orderDao.findById(orderId)
				.orElseThrow(() -> new OrderException("Order not found with order id: " + orderId));
	}

	/**
	 * Retrieves all orders.
	 *
	 * @return The list of all Order objects.
	 * @throws OrderException if there is an error while fetching the orders.
	 */
	@Override
	public List<Order> viewAllOrders() throws OrderException {
		logger.info("Viewing all orders");
		List<Order> orders = orderDao.findAll();
		if (orders != null) {
			return orders;
		} else {
			throw new OrderException("Order not found!");
		}
	}

}
