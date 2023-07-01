package com.masai.dto;

import lombok.Data;

@Data
public class UpdateOrderDto {
	private Integer orderId;
	private Integer quantity;
	private String transactionMode;
	private Integer PlanterID;
	private Integer userId;
}
