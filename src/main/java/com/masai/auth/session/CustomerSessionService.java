package com.masai.auth.session;

import com.masai.auth.exception.LoginLogoutException;

public interface CustomerSessionService {
	public CustomerSession findSession(String token) throws LoginLogoutException;

	public CustomerSession addSession(CustomerSession customerSession) throws LoginLogoutException;

	public CustomerSession deleteSession(String token) throws LoginLogoutException;
}
