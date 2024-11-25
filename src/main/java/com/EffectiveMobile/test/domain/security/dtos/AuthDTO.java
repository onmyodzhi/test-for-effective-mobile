package com.EffectiveMobile.test.domain.security.dtos;

import lombok.Value;

@Value
public class AuthDTO {
    String email;
    String password;
}