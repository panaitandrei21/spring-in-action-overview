package com.authserver.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientRegistrationRequest {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;
    private String authenticationMethod;
    private List<String> scopes;
}
