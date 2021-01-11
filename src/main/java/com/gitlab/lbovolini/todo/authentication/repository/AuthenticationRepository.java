package com.gitlab.lbovolini.todo.authentication.repository;

import com.gitlab.lbovolini.todo.common.model.User;

import java.util.Optional;

public interface AuthenticationRepository {

    Optional<User> findByUsername(String username);

    void lockAccount(String username);

    void unlockAccount(String username);
}
