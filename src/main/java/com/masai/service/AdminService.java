package com.masai.service;

import java.util.List;

import com.masai.exception.AdminException;
import com.masai.model.Admin;

public interface AdminService {

	public Admin getAdminById(Integer id) throws AdminException;

	public Admin getAdminByEmail(String email) throws AdminException;

	public List<Admin> getAllAdmin() throws AdminException;

	public Admin addAdmin(Admin admin) throws AdminException;

	public Admin deleteAdmin(Integer id) throws AdminException;

	public Admin updateAdmin(Admin admin) throws AdminException;

}
