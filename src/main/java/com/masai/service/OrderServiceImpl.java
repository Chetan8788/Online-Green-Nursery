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

	@Override
	public Order addOrder(OrderReqDto orderReqDto, Integer userId) throws OrderException {
		Planter planter = planterDao.findById(orderReqDto.getPlanterID())
				.orElseThrow(() -> new OrderException("Planter not found"));
		logger.info("planter {}", planter);
		User user = userDao.findById(userId).orElseThrow(() -> new OrderException("User not found"));
		logger.info("user {}", user);
		Integer totalStock = planter.getStock();
		if (totalStock < orderReqDto.getQuantity()) {
			throw new OrderException("There is not enough stock");
		}
		planter.setStock(totalStock - orderReqDto.getQuantity());
		Order order = new Order();
		order.setUser(user);
		order.setOrderDate(LocalDateTime.now());
		order.getPlanters().add(planter);
		order.setTransactionMode(orderReqDto.getTransactionMode());
		order.setQuantity(orderReqDto.getQuantity());
		order.setTotalCost(orderReqDto.getQuantity() * planter.getCost());
		user.getOrders().add(order);
		return orderDao.save(order);
	}

	@Override
	public Order updateOrder(UpdateOrderDto updateOrderDto) throws OrderException {
		Order orderPre = orderDao.findById(updateOrderDto.getOrderId())
				.orElseThrow(() -> new OrderException("Order not Found..."));
		Planter planterPre = orderPre.getPlanters().get(0);
		planterPre.setStock(planterPre.getStock() + orderPre.getQuantity());
		Planter planter = planterDao.findById(updateOrderDto.getPlanterID())
				.orElseThrow(() -> new OrderException("Planter not found"));
		logger.info("planter {}", planter);
		User customer = userDao.findById(updateOrderDto.getUserId())
				.orElseThrow(() -> new OrderException("User not found"));
		logger.info("customer {}", customer);
		Integer totalStock = planter.getStock();
		if (totalStock < updateOrderDto.getQuantity()) {
			throw new OrderException("There is not enough stock");
		}
		planter.setStock(totalStock - updateOrderDto.getQuantity());
		Order order = new Order();
		order.setOrderId(updateOrderDto.getOrderId());
		order.setUser(customer);
		order.setOrderDate(LocalDateTime.now());
		order.getPlanters().add(planter);
		order.setTransactionMode(updateOrderDto.getTransactionMode());
		order.setQuantity(updateOrderDto.getQuantity());
		order.setTotalCost(updateOrderDto.getQuantity() * planter.getCost());
		return orderDao.save(order);
	}

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

	@Override
	public Order viewOrder(Integer orderId) throws OrderException {
		logger.info("Viewing order with id {}", orderId);
		return orderDao.findById(orderId)
				.orElseThrow(() -> new OrderException("Order not found with order id: " + orderId));
	}

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
