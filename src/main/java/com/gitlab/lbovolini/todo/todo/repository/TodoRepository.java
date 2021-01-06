package com.gitlab.lbovolini.todo.todo.repository;

import com.gitlab.lbovolini.todo.todo.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, String>, CustomTodoRepository {
}
