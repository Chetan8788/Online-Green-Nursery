package com.masai.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginReqDto {
	@Email
	@NotNull
	private String email;
	@NotBlank
	@NotNull
	@NotEmpty
	private String passsword;

}
