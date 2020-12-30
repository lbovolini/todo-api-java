package com.gitlab.lbovolini.todo.security;

import com.gitlab.lbovolini.todo.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final AuthenticationService authenticationService;

    @Autowired
    public TokenAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails,
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = String.valueOf(authentication.getCredentials());
        Claims claims;

        try {
            claims = authenticationService.decode(token);
        } catch (Exception e) { throw new AuthenticationServiceException("Invalid token"); }

        String username = claims.getSubject();
        List<String> authorities = (List<String>) Objects.requireNonNullElse(claims.get("authorities"), List.of());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(claims.getSubject(), token,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        return this.createSuccessAuthentication(username, authentication, retrieveUser(username, authenticationToken));
    }

    @Override
    protected UserDetails retrieveUser(
            String username,
            UsernamePasswordAuthenticationToken authenticationToken) throws AuthenticationException {
        return Optional.ofNullable(username)
                .map(String::valueOf)
                .flatMap(authenticationService::findByUsername)
                .orElseThrow(() -> new AuthenticationServiceException("Cannot find username"));
    }
}
