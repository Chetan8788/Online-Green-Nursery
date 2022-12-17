package com.masai.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupResDto {
	@NotNull
	private Integer id;
	@NotBlank
	@NotEmpty
	@NotNull
	@Size(min = 2, max = 20, message = "Name should be min 2 max 20 characters long")
	private String name;
	@Email
	@NotNull
	private String email;
	@NotBlank
	@NotEmpty
	@NotNull
	@Size(min = 2, max = 20, message = "Username should be min 2 max 20 characters long")
	private String username;
}
