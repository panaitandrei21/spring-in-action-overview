package com.authserver.controller;

import com.authserver.model.ClientRegistrationRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientRegistrationController {

    private final RegisteredClientRepository clientRepository;

    public ClientRegistrationController(RegisteredClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping("/register")
    public String registerClient(@RequestBody ClientRegistrationRequest request) {
        RegisteredClient.Builder builder = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(request.getClientId())
                .clientSecret(request.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(new AuthorizationGrantType(request.getGrantType()))
                .redirectUri(request.getRedirectUri());
        builder.scope("openid");
        if (request.getScopes() != null && !request.getScopes().isEmpty()) {
            request.getScopes().forEach(builder::scope);
        }

        clientRepository.save(builder.build());
        return "Client registered successfully!";
    }
}
