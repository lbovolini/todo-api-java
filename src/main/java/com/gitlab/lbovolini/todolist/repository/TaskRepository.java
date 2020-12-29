package com.gitlab.lbovolini.todolist.repository;

import com.gitlab.lbovolini.todolist.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
