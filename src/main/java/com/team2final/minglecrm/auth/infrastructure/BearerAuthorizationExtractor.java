package com.team2final.minglecrm.auth.infrastructure;

import org.springframework.stereotype.Component;

@Component
public class BearerAuthorizationExtractor {

    private static final String AUTHORIZATION_TYPE = "Bearer ";

    public String extractAccesstoken(String header) {
        if (header != null && header.startsWith(AUTHORIZATION_TYPE)) {
            return header.substring(AUTHORIZATION_TYPE.length()).trim();
        }
        throw new IllegalArgumentException("유효하지 않은 Access Token 입니다.");
    }

}
