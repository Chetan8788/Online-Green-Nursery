package com.masai.auth.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalAuthException {
	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ErrorDetails> handleAuthException(AuthException exception, WebRequest req) {
		ErrorDetails err = new ErrorDetails();
		err.setTime(LocalDateTime.now());
		err.setDetails(req.getDescription(false));
		err.setMessage(exception.getMessage());

		return new ResponseEntity<ErrorDetails>(err, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(LoginLogoutException.class)
	public ResponseEntity<ErrorDetails> handleLoginSigninException(LoginLogoutException exception, WebRequest req) {
		ErrorDetails err = new ErrorDetails();
		err.setTime(LocalDateTime.now());
		err.setDetails(req.getDescription(false));
		err.setMessage(exception.getMessage());
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

}
