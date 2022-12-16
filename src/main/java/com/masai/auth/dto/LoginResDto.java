package com.masai.auth.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LoginResDto {
	private String token;
	private LocalDateTime timestamp;
	private String opration;
}
