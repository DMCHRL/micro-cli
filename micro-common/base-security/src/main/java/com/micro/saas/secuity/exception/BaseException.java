package com.micro.saas.secuity.exception;

/**
 * @author
 * 自定义OAuth2Exception
 */
public class BaseException extends RuntimeException {


	public BaseException() {
	}

	public BaseException(String msg) {
		super(msg);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
}
