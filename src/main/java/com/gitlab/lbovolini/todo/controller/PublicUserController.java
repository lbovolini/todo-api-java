package com.gitlab.lbovolini.todo.controller;

import com.gitlab.lbovolini.todo.model.User;
import com.gitlab.lbovolini.todo.security.UserCredentials;
import com.gitlab.lbovolini.todo.service.AuthenticationService;
import com.gitlab.lbovolini.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/public/users")
public class PublicUserController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    public PublicUserController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserCredentials userCredentials) {
        String token = authenticationService.login(userCredentials);

        return ResponseEntity.ok(token);
    }
}
