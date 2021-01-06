package com.gitlab.lbovolini.todo.authentication.service;

import com.gitlab.lbovolini.todo.authentication.AuthenticatedUser;
import com.gitlab.lbovolini.todo.authentication.UserCredentials;
import com.gitlab.lbovolini.todo.common.DefaultUser;

import java.util.Optional;

public interface AuthenticationService extends TokenAuthenticationService {

    Optional<DefaultUser> findByUsername(String username);

    AuthenticatedUser login(UserCredentials userCredentials);

    void logout(String username);
}
