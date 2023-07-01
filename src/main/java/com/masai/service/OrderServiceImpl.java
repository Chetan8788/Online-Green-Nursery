package com.masai.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.dto.OrderReqDto;
import com.masai.dto.UpdateOrderDto;
import com.masai.exception.OrderException;
import com.masai.model.User;
import com.masai.model.Order;
import com.masai.model.Planter;
import com.masai.repository.UserDao;
import com.masai.repository.OrderDao;
import com.masai.repository.PlanterDao;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	PlanterDao planterDao;
	@Autowired
	UserDao userDao;

	@Override
	public Order addOrder(OrderReqDto orderReqDto) throws OrderException {
		Planter planter = planterDao.findById(orderReqDto.getPlanterID())
				.orElseThrow(() -> new OrderException("Planter not found"));
		User user = userDao.findById(orderReqDto.getUserId())
				.orElseThrow(() -> new OrderException("User not found"));
		Integer totalStock = planter.getStock();
		if ((totalStock < orderReqDto.getQuantity()))
			throw new OrderException("There is no enough stock");
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
		User customer = userDao.findById(updateOrderDto.getUserId())
				.orElseThrow(() -> new OrderException("User not found"));
		Integer totalStock = planter.getStock();
		if ((totalStock < updateOrderDto.getQuantity()))
			throw new OrderException("There is no enough stock");
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

		} else
			throw new OrderException("Order not found with orderId : " + orderId);

	}

	@Override
	public Order viewOrder(Integer orderId) throws OrderException {

		return orderDao.findById(orderId)
				.orElseThrow(() -> new OrderException("Order not found with order id : " + orderId));

	}

	@Override
	public List<Order> viewAllOrders() throws OrderException {

		List<Order> orders = orderDao.findAll();
		if (orders != null) {
			return orders;
		} else
			throw new OrderException("Order not found !");

	}

}
