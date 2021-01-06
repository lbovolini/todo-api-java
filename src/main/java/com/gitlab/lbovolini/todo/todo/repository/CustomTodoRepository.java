package com.gitlab.lbovolini.todo.todo.repository;

import com.gitlab.lbovolini.todo.todo.model.Todo;

import java.util.Optional;

public interface CustomTodoRepository {

    void delete(String id);

    Optional<Todo> update(Todo todo);
}
