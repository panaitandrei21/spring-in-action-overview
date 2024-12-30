package com.authserver.repository;

import com.authserver.model.OAuthClient;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Primary
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

    private final OAuthClientRepository oAuthClientRepository;
    private final PasswordEncoder passwordEncoder;

    public JpaRegisteredClientRepository(OAuthClientRepository oAuthClientRepository, PasswordEncoder passwordEncoder) {
        this.oAuthClientRepository = oAuthClientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(RegisteredClient registeredClient) {
        OAuthClient client = new OAuthClient();
        client.setClientId(registeredClient.getClientId());
        client.setClientSecret(passwordEncoder.encode(registeredClient.getClientSecret()));
        client.setRedirectUri(String.join(",", registeredClient.getRedirectUris()));
        client.setGrantTypes(registeredClient.getAuthorizationGrantTypes().stream()
                .map(AuthorizationGrantType::getValue)
                .collect(Collectors.joining(",")));
        client.setScopes(String.join(",", registeredClient.getScopes()));
        client.setAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue());

        oAuthClientRepository.save(client);
    }
    @Override
    public RegisteredClient findById(String id) {
        return oAuthClientRepository.findById(Long.parseLong(id))
                .map(this::toRegisteredClient)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return toRegisteredClient(oAuthClientRepository.findByClientId(clientId));
    }

    private RegisteredClient toRegisteredClient(OAuthClient client) {
        return RegisteredClient.withId(client.getId().toString())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .redirectUri(client.getRedirectUri())
                .authorizationGrantType(new AuthorizationGrantType(client.getGrantTypes()))
                .scopes(scopes -> scopes.addAll(Arrays.asList(client.getScopes().split(","))))
                .clientAuthenticationMethod(new ClientAuthenticationMethod(client.getAuthenticationMethod()))
                .build();
    }
}
