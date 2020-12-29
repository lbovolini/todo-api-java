package com.gitlab.lbovolini.todo.repository;

import com.gitlab.lbovolini.todo.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
