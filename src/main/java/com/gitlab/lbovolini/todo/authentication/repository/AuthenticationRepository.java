package com.gitlab.lbovolini.todo.authentication.repository;

import com.gitlab.lbovolini.todo.common.DefaultUser;

import java.util.Optional;

public interface AuthenticationRepository {

    Optional<DefaultUser> findByUsername(String username);
}
