package com.gitlab.lbovolini.todo.repository;

import com.gitlab.lbovolini.todo.model.User;

import java.util.Optional;

public interface CustomUserRepository {

    Optional<User> update(User user);
}
