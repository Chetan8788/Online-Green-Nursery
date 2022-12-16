package com.masai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.auth.Authorization;
import com.masai.auth.dto.SignupReqDto;
import com.masai.auth.dto.SignupResDto;
import com.masai.model.Admin;
import com.masai.service.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {
	@Autowired
	AdminService adminService;
	@Autowired
	Authorization authorization;

	@GetMapping(value = "{id}")
	public ResponseEntity<Admin> getAdminById(@PathVariable Integer id, @RequestHeader String token) {
		authorization.isAuthorized(token, "admin");
		Admin foundAdmin = adminService.getAdminById(id);
		return new ResponseEntity<Admin>(foundAdmin, HttpStatus.OK);
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Admin>> getAllAdmin(@RequestHeader String token) {

		authorization.isAuthorized(token, "admin");
		List<Admin> foundAdminList = adminService.getAllAdmin();
		return new ResponseEntity<List<Admin>>(foundAdminList, HttpStatus.OK);
	}

	@PostMapping(value = "")
	public ResponseEntity<Admin> addAdmin(@Valid @RequestBody SignupReqDto signupReqDto, @RequestHeader String token) {
		authorization.isAuthorized(token, "admin");
		Admin admin = new Admin();
		admin.setName(signupReqDto.getName());
		admin.setEmail(signupReqDto.getEmail());
		admin.setUsername(signupReqDto.getUsername());
		admin.setPassword(signupReqDto.getPassword());
		Admin savesAdmin = adminService.addAdmin(admin);
		return new ResponseEntity<Admin>(savesAdmin, HttpStatus.CREATED);

	}

	@PutMapping(value = "")
	public ResponseEntity<Admin> updateAdmin(@Valid @RequestBody SignupResDto signupResDto,
			@RequestHeader String token) {
		authorization.isAuthorized(token, "admin");
		Admin admin = new Admin();
		admin.setName(signupResDto.getName());
		admin.setEmail(signupResDto.getEmail());
		admin.setUsername(signupResDto.getUsername());
		admin.setId(signupResDto.getId());
		Admin savesAdmin = adminService.addAdmin(admin);
		return new ResponseEntity<Admin>(savesAdmin, HttpStatus.OK);
	}

	@DeleteMapping(value = "")
	public ResponseEntity<SignupResDto> deleteAdmin(@RequestParam Integer id, @RequestHeader String token) {
		int ider = authorization.isAuthorized(token, "admin");
		Admin admin = adminService.deleteAdmin(id);
		SignupResDto signupResDto = new SignupResDto();
		signupResDto.setId(admin.getId());
		signupResDto.setName(admin.getName());
		signupResDto.setEmail(admin.getEmail());
		signupResDto.setUsername(admin.getUsername());
		return new ResponseEntity<SignupResDto>(signupResDto, HttpStatus.OK);
	}

}
