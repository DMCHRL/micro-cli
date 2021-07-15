package com.micro.saas.auth.handler;

import com.micro.saas.core.api.ApiException;
import com.micro.saas.core.api.R;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * 登录失败返回类
 */
public class OAuthWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<R<Void>> translate(Exception e)  {
        if (e instanceof ApiException) {
            ApiException e1 = (ApiException) e;
            return ResponseEntity.ok(R.failed(e1.getErrorCode()));
        }else{
            return ResponseEntity.ok(R.failed(e.getMessage()));
        }
    }
}
