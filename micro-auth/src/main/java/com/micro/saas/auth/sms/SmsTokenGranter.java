package com.micro.saas.auth.sms;

import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmsTokenGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "sms";

    private final AuthenticationProvider authenticationProvider;

    public SmsTokenGranter(AuthenticationProvider authenticationProvider, AuthorizationServerTokenServices tokenServices,
                           ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory){
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String phone = parameters.get("phone");
        String code = parameters.get("code");
        Authentication userAuth = new SmsAuthenticationToken(phone, code);
        try {
            userAuth = authenticationProvider.authenticate(userAuth);
        }
        catch (AccountStatusException ase) {
            throw new InvalidGrantException(ase.getMessage());
        }
        catch (BadCredentialsException e) {
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user:");
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}

