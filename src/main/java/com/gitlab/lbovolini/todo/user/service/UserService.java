package com.gitlab.lbovolini.todo.user.service;

import com.gitlab.lbovolini.todo.common.CrudService;
import com.gitlab.lbovolini.todo.common.model.User;

import java.util.Optional;

public interface UserService extends CrudService<User> {

    Optional<User> findByUsername(String username);

    User register(User user);
}
