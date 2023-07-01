package com.masai.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderResDto {
	private Integer OrderId;
	private LocalDateTime orderDate;
	private Integer quantity;
	private Double totalCost;
	private String transactionMode;
	private Integer PlanterID;
	private Integer userId;
}
