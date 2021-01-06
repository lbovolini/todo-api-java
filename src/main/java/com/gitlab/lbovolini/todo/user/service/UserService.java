package com.gitlab.lbovolini.todo.user.service;

import com.gitlab.lbovolini.todo.common.CrudService;
import com.gitlab.lbovolini.todo.user.model.User;
import com.gitlab.lbovolini.todo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements CrudService<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        user.setPassword(hashedPassword(user));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> update(User user) {
        user.setPassword(hashedPassword(user));
        return userRepository.update(user);
    }

    private String hashedPassword(User user) {
        return BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
    }
}
