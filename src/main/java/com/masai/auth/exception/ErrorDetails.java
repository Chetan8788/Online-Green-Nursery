package com.masai.auth.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorDetails {
	private LocalDateTime time;
	private String message;
	private String details;
}
