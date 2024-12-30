package com.authserver.repository;

import com.authserver.model.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthClientRepository extends JpaRepository<OAuthClient, Long> {
    OAuthClient findByClientId(String clientId);
}