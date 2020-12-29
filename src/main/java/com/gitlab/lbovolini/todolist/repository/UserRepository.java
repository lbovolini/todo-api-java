package com.gitlab.lbovolini.todolist.repository;

import com.gitlab.lbovolini.todolist.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
