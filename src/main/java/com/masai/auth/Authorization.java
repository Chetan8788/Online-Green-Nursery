package com.masai.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.auth.exception.AuthException;
import com.masai.auth.session.AdminSession;
import com.masai.auth.session.AdminSessionDao;
import com.masai.auth.session.CustomerSession;
import com.masai.auth.session.CustomerSessionDao;

@Service
public class Authorization {
	@Autowired
	CustomerSessionDao csd;
	@Autowired
	AdminSessionDao asd;

	public Integer isAuthorized(String token, String user) throws AuthException {
		if (user.equals("admin")) {
			AdminSession as = asd.findById(token).orElseThrow(() -> new AuthException("unauthorized"));
			return as.getAdminId();
		} else if (user.equals("customer")) {
			CustomerSession cs = csd.findById(token).orElseThrow(() -> new AuthException("unauthorized"));
			return cs.getCustomerId();
		}
		throw new AuthException("unauthorized");

	}

	public Boolean isAuthorized(String token) throws AuthException {

		Optional<AdminSession> as = asd.findById(token);
		if (as.isPresent())
			return true;
		Optional<CustomerSession> cs = csd.findById(token);
		if (cs.isPresent())
			return true;
		throw new AuthException("unauthorized");

	}

}
