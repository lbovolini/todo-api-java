package com.gitlab.lbovolini.todo.repository;

import com.gitlab.lbovolini.todo.model.Task;

import java.util.Optional;

public interface CustomTaskRepository {

    Optional<Task> update(Task task);
}
