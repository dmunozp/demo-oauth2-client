package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.security.oauth2.client.OAuth2AuthorizeRequest.withClientRegistrationId;

@Service
public class DefaultTokenService implements TokenService
{
    @Autowired
    private AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager;

    public OAuth2AccessToken getOAuth2AccessToken()
    {
        return Objects.requireNonNull(clientManager.authorize(withClientRegistrationId("keycloak").principal("Keycloak").build())).getAccessToken();
    }

    @Override
    public Token allocateToken(String extendedInformation) {
        return null;
    }

    @Override
    public Token verifyToken(String key) {
        return null;
    }
}
