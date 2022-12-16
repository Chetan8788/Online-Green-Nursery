package com.masai.auth.session;

import com.masai.auth.exception.LoginLogoutException;

public interface AdminSessionService {

	public AdminSession findSessionByToken(String token) throws LoginLogoutException;

	public boolean isAdminIdLogedin(Integer adminId) throws LoginLogoutException;

	public AdminSession addSession(AdminSession adminSession) throws LoginLogoutException;

	public AdminSession deleteSession(String token) throws LoginLogoutException;

}
