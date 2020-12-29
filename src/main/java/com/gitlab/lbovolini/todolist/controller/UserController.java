package com.gitlab.lbovolini.todolist.controller;

import com.gitlab.lbovolini.todolist.model.User;
import com.gitlab.lbovolini.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController implements CrudController<User> {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {

        Optional<User> userOptional = userService.findById(id);

        return userOptional.stream()
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> userList = userService.findAll();

        if (userList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userList);
    }

    @Override
    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        User savedUser = userService.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @Override
    @PutMapping
    public ResponseEntity<?> update(@RequestBody User user) {
        userService.update(user);

        return ResponseEntity.ok().build();
    }
}
