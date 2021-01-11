package com.gitlab.lbovolini.todo.authentication.service;

import com.gitlab.lbovolini.todo.authentication.AuthenticatedUser;
import com.gitlab.lbovolini.todo.authentication.UserCredentials;
import com.gitlab.lbovolini.todo.common.model.User;

import java.util.Optional;

public interface AuthenticationService extends TokenAuthenticationService {

    Optional<User> findByUsername(String username);

    AuthenticatedUser login(UserCredentials userCredentials);

    void logout(String username);
}
