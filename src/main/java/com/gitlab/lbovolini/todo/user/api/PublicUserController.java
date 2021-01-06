package com.gitlab.lbovolini.todo.user.api;

import com.gitlab.lbovolini.todo.user.service.UserService;
import com.gitlab.lbovolini.todo.user.model.User;
import com.gitlab.lbovolini.todo.authentication.AuthenticatedUser;
import com.gitlab.lbovolini.todo.authentication.UserCredentials;
import com.gitlab.lbovolini.todo.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/public/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<AuthenticatedUser> login(@Valid @RequestBody UserCredentials userCredentials) {
        AuthenticatedUser authenticatedUser = authenticationService.login(userCredentials);

        return ResponseEntity.ok(authenticatedUser);
    }
}
