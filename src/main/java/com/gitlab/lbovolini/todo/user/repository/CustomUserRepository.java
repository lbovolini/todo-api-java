package com.gitlab.lbovolini.todo.user.repository;

import com.gitlab.lbovolini.todo.user.model.User;

import java.util.Optional;

public interface CustomUserRepository {

    void delete(String id);

    Optional<User> update(User user);
}
