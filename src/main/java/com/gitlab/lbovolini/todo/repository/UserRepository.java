package com.gitlab.lbovolini.todo.repository;

import com.gitlab.lbovolini.todo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
