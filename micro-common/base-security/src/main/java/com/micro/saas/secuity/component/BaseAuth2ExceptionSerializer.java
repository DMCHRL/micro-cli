package com.micro.saas.secuity.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.micro.saas.core.constant.CommonConstant;
import com.micro.saas.secuity.exception.BaseAuth2Exception;
import lombok.SneakyThrows;

/**
 * @author
 * <p>
 * OAuth2 异常格式化
 */
public class BaseAuth2ExceptionSerializer extends StdSerializer<BaseAuth2Exception> {
	public BaseAuth2ExceptionSerializer() {
		super(BaseAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(BaseAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", CommonConstant.FAIL);
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("data", value.getErrorCode());
		gen.writeEndObject();
	}
}
