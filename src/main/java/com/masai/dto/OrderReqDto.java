package com.masai.dto;

import lombok.Data;

@Data
public class OrderReqDto {
	private Integer quantity;
	private String transactionMode;
	private Integer PlanterID;
	
}
