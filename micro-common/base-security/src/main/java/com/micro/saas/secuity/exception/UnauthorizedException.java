package com.micro.saas.secuity.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.micro.saas.secuity.component.BaseAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author
 */
@JsonSerialize(using = BaseAuth2ExceptionSerializer.class)
public class UnauthorizedException extends BaseAuth2Exception {

	public UnauthorizedException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "unauthorized";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.UNAUTHORIZED.value();
	}

}
