package com.gitlab.lbovolini.todo.authentication;

import java.time.ZonedDateTime;

public class AuthenticatedUser {

    private final String id;
    private final String username;
    private final String token;
    private final ZonedDateTime expiration;

    public AuthenticatedUser(String id, String username, String token, ZonedDateTime expiration) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.expiration = expiration;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public ZonedDateTime getExpiration() {
        return expiration;
    }
}
