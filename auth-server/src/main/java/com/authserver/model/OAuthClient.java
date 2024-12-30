package com.authserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
public class OAuthClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantTypes;
    private String scopes;
    private String authenticationMethod;

}
