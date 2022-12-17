package com.masai.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignupReqDto {
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
	@NotBlank
	@NotEmpty
	@NotNull
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$", message = "Password should be minimum 8-20 characters, at least one letter, one number and one special character")
	private String password;

}
