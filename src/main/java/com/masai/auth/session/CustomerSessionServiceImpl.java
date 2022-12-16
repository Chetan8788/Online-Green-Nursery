package com.masai.auth.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.auth.exception.LoginLogoutException;

@Service
public class CustomerSessionServiceImpl implements CustomerSessionService {
	@Autowired
	CustomerSessionDao customerSessionDao;

	@Override
	public CustomerSession findSession(String token) throws LoginLogoutException {

		return customerSessionDao.findById(token)
				.orElseThrow(() -> new LoginLogoutException("Please provide valid token"));
	}

	@Override
	public CustomerSession addSession(CustomerSession customerSession) throws LoginLogoutException {
		if (customerSession != null)
			return customerSessionDao.save(customerSession);
		else
			throw new LoginLogoutException("Please provide valid details");

	}

	@Override
	public CustomerSession deleteSession(String token) throws LoginLogoutException {
		CustomerSession adminSession = customerSessionDao.findById(token)
				.orElseThrow(() -> new LoginLogoutException("Please provide valid token"));

		customerSessionDao.deleteById(token);
		return adminSession;

	}

}
