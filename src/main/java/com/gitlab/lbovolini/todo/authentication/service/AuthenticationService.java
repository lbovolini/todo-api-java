package com.gitlab.lbovolini.todo.authentication.service;

import com.gitlab.lbovolini.todo.authentication.AuthenticatedUser;
import com.gitlab.lbovolini.todo.authentication.UserCredentials;

public interface AuthenticationService extends TokenAuthenticationService {

    AuthenticatedUser login(UserCredentials userCredentials);
}
