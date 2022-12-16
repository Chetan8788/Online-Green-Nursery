package com.masai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.AdminException;
import com.masai.model.Admin;
import com.masai.repository.AdminDao;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminDao adminDao;

	@Override
	public Admin getAdminById(Integer id) throws AdminException {

		return adminDao.findById(id).orElseThrow(() -> new AdminException("please provide valid details"));
	}

	@Override
	public Admin getAdminByEmail(String email) throws AdminException {

		return adminDao.findByEmail(email).orElseThrow(() -> new AdminException("please provide valid details"));
	}

	@Override
	public List<Admin> getAllAdmin() throws AdminException {
		List<Admin> admins = adminDao.findAll();
		if (admins.isEmpty())
			throw new AdminException("No admin found");
		return admins;
	}

	@Override
	public Admin addAdmin(Admin admin) throws AdminException {
		if (admin == null)
			throw new AdminException("please provide valid details");
		return adminDao.save(admin);
	}

	@Override
	public Admin deleteAdmin(Integer id) throws AdminException {
		Admin foundAdmin = adminDao.findById(id).orElseThrow(() -> new AdminException("please provide valid details"));
		adminDao.delete(foundAdmin);
		return foundAdmin;
	}

	@Override
	public Admin updateAdmin(Admin admin) throws AdminException {
		Admin foundAdmin = adminDao.findById(admin.getId())
				.orElseThrow(() -> new AdminException("please provide valid details"));

		return adminDao.save(admin);
	}

}
