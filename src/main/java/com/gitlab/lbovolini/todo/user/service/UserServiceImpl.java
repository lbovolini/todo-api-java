package com.gitlab.lbovolini.todo.user.service;

import com.gitlab.lbovolini.todo.common.model.User;
import com.gitlab.lbovolini.todo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("isOwnerById(#id)")
    public void delete(String id) {
        userRepository.delete(id);
    }

    @Override
    @PreAuthorize("isOwnerById(#id)")
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    @PostAuthorize("isOwnerByUsername(#username)")
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User register(User user) {
        user.setPassword(hashedPassword(user));
        return userRepository.save(user);
    }

    @Override
    @PreAuthorize("#user.id == null")
    public User save(User user) {
        user.setPassword(hashedPassword(user));
        return userRepository.save(user);
    }

    @Override
    @PreAuthorize("#isOwner(#user)")
    public Optional<User> update(User user) {
        user.setPassword(hashedPassword(user));
        return userRepository.update(user);
    }

    // !todo mover
    private String hashedPassword(User user) {
        return BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
    }
}
