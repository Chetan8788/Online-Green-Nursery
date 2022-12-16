package com.masai.auth.session;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.auth.exception.LoginLogoutException;

@Service
public class AdminSessionServiceImpl implements AdminSessionService {
	@Autowired
	AdminSessionDao adminSessionDao;

	@Override
	public AdminSession findSessionByToken(String token) throws LoginLogoutException {
		AdminSession adminSession = adminSessionDao.findById(token)
				.orElseThrow(() -> new LoginLogoutException("Please provide valid token"));

		return adminSession;
	}

	@Override
	public boolean isAdminIdLogedin(Integer adminId) throws LoginLogoutException {
		Optional<AdminSession> adminSession = adminSessionDao.findByAdminId(adminId);

		return adminSession.isPresent();
	}

	@Override
	public AdminSession addSession(AdminSession adminSession) throws LoginLogoutException {
		if (adminSession != null)
			return adminSessionDao.save(adminSession);
		else
			throw new LoginLogoutException("Please provide valid details");

	}

	@Override
	public AdminSession deleteSession(String token) throws LoginLogoutException {
		AdminSession adminSession = adminSessionDao.findById(token)
				.orElseThrow(() -> new LoginLogoutException("Please provide valid token"));

		adminSessionDao.deleteById(token);
		return adminSession;

	}

}
