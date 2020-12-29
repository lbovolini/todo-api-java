package com.gitlab.lbovolini.todolist.service;

import com.gitlab.lbovolini.todolist.model.User;
import com.gitlab.lbovolini.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public User save(User task) {
        return userRepository.save(task);
    }

    @Override
    public void update(User task) {
        userRepository.save(task);
    }
}
