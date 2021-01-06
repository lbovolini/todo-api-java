package com.gitlab.lbovolini.todo.authentication.service;

import io.jsonwebtoken.Claims;

public interface TokenAuthenticationService {

    Claims decode(String token);
}
