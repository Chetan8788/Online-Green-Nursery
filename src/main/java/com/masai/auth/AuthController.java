package com.masai.auth;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.auth.dto.LoginReqDto;
import com.masai.auth.dto.LoginResDto;
import com.masai.auth.dto.SignupReqDto;
import com.masai.auth.exception.LoginLogoutException;
import com.masai.auth.session.AdminSession;
import com.masai.auth.session.AdminSessionService;
import com.masai.auth.session.CustomerSession;
import com.masai.auth.session.CustomerSessionService;
import com.masai.model.Admin;
import com.masai.service.AdminService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AdminService adminService;
	@Autowired
	AdminSessionService adminSessionService;
	@Autowired
	CustomerSessionService customerSessionService;

	@PostMapping(value = "/loginAsCustomer")
	public ResponseEntity<LoginResDto> loginAsCustomer(@Valid @RequestBody LoginReqDto loginReqDto) {

		Admin admin = adminService.getAdminByEmail(loginReqDto.getEmail());
		LocalDateTime timestamp = LocalDateTime.now();
		String token;
		while (true) {
			token = "" + (int) Math.random() * 100000;
			try {
				customerSessionService.findSession(token);
			} catch (LoginLogoutException e) {
				break;
			}
		}
		CustomerSession customerSession = new CustomerSession();
		customerSession.setCustomerId(admin.getId());
		customerSession.setTimestamp(timestamp);
		customerSession.setToken(token);
		customerSessionService.addSession(customerSession);
		LoginResDto loginResDto = new LoginResDto();
		loginResDto.setTimestamp(timestamp);
		loginResDto.setToken(token);
		loginResDto.setOpration("login");
		return new ResponseEntity<LoginResDto>(loginResDto, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/loginAsAdmin")
	public ResponseEntity<LoginResDto> loginAsAdmin(@Valid @RequestBody LoginReqDto loginReqDto) {

		Admin admin = adminService.getAdminByEmail(loginReqDto.getEmail());
		LocalDateTime timestamp = LocalDateTime.now();
		String token;
		while (true) {
			token = "" + (int) (Math.random() * 100000);
			try {
				adminSessionService.findSessionByToken(token);
			} catch (LoginLogoutException e) {
				break;
			}

		}
		AdminSession adminSession = new AdminSession();
		adminSession.setAdminId(admin.getId());
		adminSession.setTimestamp(timestamp);
		adminSession.setToken(token);
		adminSessionService.addSession(adminSession);
		LoginResDto loginResDto = new LoginResDto();
		loginResDto.setTimestamp(timestamp);
		loginResDto.setToken(token);
		loginResDto.setOpration("login");
		return new ResponseEntity<LoginResDto>(loginResDto, HttpStatus.ACCEPTED);
	}

	@DeleteMapping(value = "/logout")
	public ResponseEntity<LoginResDto> logout(@RequestHeader String token) {
		try {
			AdminSession adminSession = adminSessionService.deleteSession(token);
		} catch (LoginLogoutException e) {
			CustomerSession customerSession = customerSessionService.deleteSession(token);
		}
		LoginResDto loginResDto = new LoginResDto();
		loginResDto.setTimestamp(LocalDateTime.now());
		loginResDto.setToken(token);
		loginResDto.setOpration("logout");
		return new ResponseEntity<LoginResDto>(loginResDto, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/signup")
	public ResponseEntity<Admin> signupAsCustomer(@Valid @RequestBody SignupReqDto singnupDto) {
		Admin admin = new Admin();
		admin.setName(singnupDto.getName());
		admin.setUsername(singnupDto.getUsername());
		admin.setEmail(singnupDto.getEmail());
		admin.setPassword(singnupDto.getPassword());
		Admin createdAdmin = adminService.addAdmin(admin);
		return new ResponseEntity<Admin>(createdAdmin, HttpStatus.CREATED);

	}

}
