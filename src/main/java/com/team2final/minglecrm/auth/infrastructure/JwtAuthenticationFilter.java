package com.team2final.minglecrm.auth.infrastructure;

import com.team2final.minglecrm.auth.dto.Subject;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final BearerAuthorizationExtractor extractor;

    public JwtAuthenticationFilter(String processUrl,
                                   BearerAuthorizationExtractor extractor,
                                   AuthenticationManager authenticationManager) {
        super(processUrl);
        this.extractor = extractor;
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(successHandler());
    }

    private AuthenticationSuccessHandler successHandler() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> successHandler");
        return (request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> attemptAuthentication");
        String atk = extractor.extractAccesstoken(request.getHeader(HttpHeaders.AUTHORIZATION));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> attemptAuthentication atk , "+atk);
        Authentication authentication = new JwtAuthenticationToken(atk);
        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws ServletException, IOException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> successfulAuthentication");
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
