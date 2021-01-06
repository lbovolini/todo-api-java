package com.gitlab.lbovolini.todo.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

public class UserCredentials {

    @NotBlank
    private final String username;
    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    private final String password;

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
