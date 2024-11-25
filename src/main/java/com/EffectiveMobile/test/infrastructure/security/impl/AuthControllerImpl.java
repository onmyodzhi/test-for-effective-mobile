package com.EffectiveMobile.test.infrastructure.security.impl;

import com.EffectiveMobile.test.configurations.KeycloakConfig;
import com.EffectiveMobile.test.domain.security.dtos.AuthDTO;
import com.EffectiveMobile.test.infrastructure.security.AuthController;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthControllerImpl implements AuthController {

    KeycloakConfig keycloakConfig;

    @Override
    public String auth(@RequestBody AuthDTO authDto) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var body = "client_id=" + keycloakConfig.getClientId() +
                "&email=" + authDto.getEmail() +
                "&password=" + authDto.getPassword() +
                "&grant_type=" + keycloakConfig.getGrantType();

        var requestEntity = new HttpEntity<>(body, headers);

        var response = new RestTemplate()
                .exchange(
                        keycloakConfig.getResourceServerUrl(),
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                );

        if (response.getStatusCode().value() == 200) {
            return response.getBody();
        }

        return null;
    }
}