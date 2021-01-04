package com.gitlab.lbovolini.todo.repository;

import com.gitlab.lbovolini.todo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository {

    Optional<User> findByUsername(String username);
}
