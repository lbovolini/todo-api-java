package com.gitlab.lbovolini.todo.user.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository {

    Optional<User> findByUsername(String username);
}
