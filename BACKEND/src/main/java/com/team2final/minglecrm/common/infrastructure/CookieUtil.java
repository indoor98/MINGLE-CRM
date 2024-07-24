package com.team2final.minglecrm.common.infrastructure;

import com.team2final.minglecrm.auth.dto.response.TokenResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CookieUtil {

    public String createRtkCookie(TokenResponse tokenResponse) {
        Date now = new Date();
        long expirationTimeMillis = tokenResponse.getRtkExpiration().getTime();
        long currentTimeMillis = now.getTime();

        int maxAgeInSeconds = (int) ((expirationTimeMillis - currentTimeMillis) / 1000);

        ResponseCookie cookie = ResponseCookie
                .from("rtk", tokenResponse.getRtk())
                .path("/")
                .httpOnly(true)
                .maxAge(maxAgeInSeconds)
                .build();

        return cookie.toString();
    }

    public String createDeleteCookie() {
        ResponseCookie cookie = ResponseCookie
                .from("rtk", null)
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();

        return cookie.toString();
    }
}
